package org.tako.data.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Streamable<T> extends Iterable<T>, Supplier<Stream<T>> {

	/**
	 * Returns an empty {@link Streamable}.
	 *
	 * @return will never be {@literal null}.
	 */
	static <T> Streamable<T> empty() {
		return Collections::emptyIterator;
	}

	/**
	 * Returns a {@link Streamable} with the given elements.
	 *
	 * @param t the elements to return.
	 * @return
	 */
	@SafeVarargs
	static <T> Streamable<T> of(T... t) {
		return () -> Arrays.asList(t).iterator();
	}

	/**
	 * Returns a {@link Streamable} for the given {@link Iterable}.
	 *
	 * @param iterable must not be {@literal null}.
	 * @return
	 */
	static <T> Streamable<T> of(Iterable<T> iterable) {

		assert iterable != null: "Iterable must not be null!";

		return iterable::iterator;
	}

	static <T> Streamable<T> of(Supplier<? extends Stream<T>> supplier) {
		return LazyStreamable.of(supplier);
	}

	/**
	 * Creates a non-parallel {@link Stream} of the underlying {@link Iterable}.
	 *
	 * @return will never be {@literal null}.
	 */
	default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	/**
	 * Returns a new {@link Streamable} that will apply the given {@link Function} to the current one.
	 *
	 * @param mapper must not be {@literal null}.
	 * @return
	 * @see Stream#map(Function)
	 */
	default <R> Streamable<R> map(Function<? super T, ? extends R> mapper) {

		assert mapper != null: "Mapping function must not be null!";

		return Streamable.of(() -> stream().map(mapper));
	}

	/**
	 * Returns a new {@link Streamable} that will apply the given {@link Function} to the current one.
	 *
	 * @param mapper must not be {@literal null}.
	 * @return
	 * @see Stream#flatMap(Function)
	 */
	default <R> Streamable<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {

		assert mapper != null: "Mapping function must not be null!";

		return Streamable.of(() -> stream().flatMap(mapper));
	}

	/**
	 * Returns a new {@link Streamable} that will apply the given filter {@link Predicate} to the current one.
	 *
	 * @param predicate must not be {@literal null}.
	 * @return
	 * @see Stream#filter(Predicate)
	 */
	default Streamable<T> filter(Predicate<? super T> predicate) {

		assert predicate != null: "Filter predicate must not be null!";

		return Streamable.of(() -> stream().filter(predicate));
	}

	/**
	 * Returns whether the current {@link Streamable} is empty.
	 * 
	 * @return
	 */
	default boolean isEmpty() {
		return !iterator().hasNext();
	}

	/**
	 * Creates a new {@link Streamable} from the current one and the given {@link Stream} concatenated.
	 * 
	 * @param stream must not be {@literal null}.
	 * @return
	 * @since 2.1
	 */
	default Streamable<T> and(Supplier<? extends Stream<? extends T>> stream) {

		assert stream != null: "Stream must not be null!";

		return Streamable.of(() -> Stream.concat(this.stream(), stream.get()));
	}

	
	default Stream<T> get() {
		return stream();
	}
}