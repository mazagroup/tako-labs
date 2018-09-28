package org.tako.data.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tako.commons.core.GenericTypeResolver;
import org.tako.commons.util.Assert;
import org.tako.commons.util.ConcurrentReferenceHashMap;
import org.tako.commons.util.ConcurrentReferenceHashMap.ReferenceType;

import java.util.Set;

/**
 * {@link TypeInformation} for a plain {@link Class}.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClassTypeInformation<S> extends TypeDiscoverer<S> {

	public static final ClassTypeInformation<Collection> COLLECTION = new ClassTypeInformation(Collection.class);
	public static final ClassTypeInformation<List> LIST = new ClassTypeInformation(List.class);
	public static final ClassTypeInformation<Set> SET = new ClassTypeInformation(Set.class);
	public static final ClassTypeInformation<Map> MAP = new ClassTypeInformation(Map.class);
	public static final ClassTypeInformation<Object> OBJECT = new ClassTypeInformation(Object.class);

	private static final Map<Class<?>, ClassTypeInformation<?>> CACHE = new ConcurrentReferenceHashMap<>(64,
			ReferenceType.WEAK);

	static {
		Arrays.asList(COLLECTION, LIST, SET, MAP, OBJECT).forEach(it -> CACHE.put(it.getType(), it));
	}

	private final Class<S> type;

	/**
	 * Simple factory method to easily create new instances of {@link ClassTypeInformation}.
	 *
	 * @param <S>
	 * @param type must not be {@literal null}.
	 * @return
	 */
	public static <S> ClassTypeInformation<S> from(Class<S> type) {

		Assert.notNull(type, "Type must not be null!");

		return (ClassTypeInformation<S>) CACHE.computeIfAbsent(type, ClassTypeInformation::new);
	}

	/**
	 * Creates a {@link TypeInformation} from the given method's return type.
	 *
	 * @param method must not be {@literal null}.
	 * @return
	 */
	public static <S> TypeInformation<S> fromReturnTypeOf(Method method) {

		Assert.notNull(method, "Method must not be null!");
		return (TypeInformation<S>) ClassTypeInformation.from(method.getDeclaringClass())
				.createInfo(method.getGenericReturnType());
	}

	/**
	 * Creates {@link ClassTypeInformation} for the given type.
	 *
	 * @param type
	 */
	ClassTypeInformation(Class<S> type) {
		super(ProxyUtils.getUserClass(type), getTypeVariableMap(type));
		this.type = type;
	}

	/**
	 * Little helper to allow us to create a generified map, actually just to satisfy the compiler.
	 *
	 * @param type must not be {@literal null}.
	 * @return
	 */
	private static Map<TypeVariable<?>, Type> getTypeVariableMap(Class<?> type) {
		return getTypeVariableMap(type, new HashSet<>());
	}

	private static Map<TypeVariable<?>, Type> getTypeVariableMap(Class<?> type, Collection<Type> visited) {

		if (visited.contains(type)) {
			return Collections.emptyMap();
		} else {
			visited.add(type);
		}

		Map<TypeVariable, Type> source = GenericTypeResolver.getTypeVariableMap(type);
		Map<TypeVariable<?>, Type> map = new HashMap<>(source.size());

		for (Entry<TypeVariable, Type> entry : source.entrySet()) {

			Type value = entry.getValue();
			map.put(entry.getKey(), entry.getValue());

			if (value instanceof Class) {

				for (Entry<TypeVariable<?>, Type> nestedEntry : getTypeVariableMap((Class<?>) value, visited).entrySet()) {
					if (!map.containsKey(nestedEntry.getKey())) {
						map.put(nestedEntry.getKey(), nestedEntry.getValue());
					}
				}
			}
		}

		return map;
	}

	
	@Override
	public Class<S> getType() {
		return type;
	}

	
	@Override
	public ClassTypeInformation<?> getRawTypeInformation() {
		return this;
	}

	
	@Override
	public boolean isAssignableFrom(TypeInformation<?> target) {
		return getType().isAssignableFrom(target.getType());
	}

	
	@Override
	public TypeInformation<? extends S> specialize(ClassTypeInformation<?> type) {
		return (TypeInformation<? extends S>) type;
	}

	
	@Override
	public String toString() {
		return type.getName();
	}
}
