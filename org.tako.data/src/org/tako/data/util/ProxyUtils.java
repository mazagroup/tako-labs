package org.tako.data.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * Proxy type detection utilities, extensible via {@link ProxyDetector} registered via Spring factories.
 * 
 */
@UtilityClass
public class ProxyUtils {

	private static Map<Class<?>, Class<?>> USER_TYPES = new ConcurrentReferenceHashMap<>();

	private static final List<ProxyDetector> DETECTORS = SpringFactoriesLoader.loadFactories(ProxyDetector.class,
			ProxyUtils.class.getClassLoader());

	static {
		DETECTORS.add(ClassUtils::getUserClass);
	}

	/**
	 * Returns the user class for the given type.
	 * 
	 * @param type must not be {@literal null}.
	 * @return
	 */
	public static Class<?> getUserClass(Class<?> type) {

		Assert.notNull(type, "Type must not be null!");

		return USER_TYPES.computeIfAbsent(type, it -> {

			Class<?> result = it;

			for (ProxyDetector proxyDetector : DETECTORS) {
				result = proxyDetector.getUserType(result);
			}

			return result;
		});
	}

	/**
	 * Returns the user class for the given source object.
	 * 
	 * @param source must not be {@literal null}.
	 * @return
	 */
	public static Class<?> getUserClass(Object source) {

		Assert.notNull(source, "Source object must not be null!");

		return getUserClass(AopUtils.getTargetClass(source));
	}

	/**
	 * SPI to extend Spring's default proxy detection capabilities.
	 *
	 * @author Oliver Gierke
	 */
	public interface ProxyDetector {

		/**
		 * Returns the user class for the given type.
		 * 
		 * @param type will never be {@literal null}.
		 * @return
		 */
		Class<?> getUserType(Class<?> type);
	}
}
