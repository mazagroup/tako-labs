package org.tako.data.commons.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface IStreamable<T> extends Iterable<T>, Supplier<Stream<T>> {

	/**
	 * Returns an empty {@link IStreamable}.
	 *
	 * @return will never be {@literal null}.
	 */
	static <T> IStreamable<T> empty() {
		return Collections::emptyIterator;
	}

	/**
	 * Returns a {@link IStreamable} with the given elements.
	 *
	 * @param t the elements to return.
	 * @return
	 */
	@SafeVarargs
	static <T> IStreamable<T> of(T... t) {
		return () -> Arrays.asList(t).iterator();
	}

	/**
	 * Returns a {@link IStreamable} for the given {@link Iterable}.
	 *
	 * @param iterable must not be {@literal null}.
	 * @return
	 */
	static <T> IStreamable<T> of(Iterable<T> iterable) {

		assert iterable != null: "Iterable must not be null!";

		return iterable::iterator;
	}

	static <T> IStreamable<T> of(Supplier<? extends Stream<T>> supplier) {
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
	 * Returns a new {@link IStreamable} that will apply the given {@link Function} to the current one.
	 *
	 * @param mapper must not be {@literal null}.
	 * @return
	 * @see Stream#map(Function)
	 */
	default <R> IStreamable<R> map(Function<? super T, ? extends R> mapper) {

		assert mapper != null: "Mapping function must not be null!";

		return IStreamable.of(() -> stream().map(mapper));
	}

	/**
	 * Returns a new {@link IStreamable} that will apply the given {@link Function} to the current one.
	 *
	 * @param mapper must not be {@literal null}.
	 * @return
	 * @see Stream#flatMap(Function)
	 */
	default <R> IStreamable<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {

		assert mapper != null: "Mapping function must not be null!";

		return IStreamable.of(() -> stream().flatMap(mapper));
	}

	/**
	 * Returns a new {@link IStreamable} that will apply the given filter {@link Predicate} to the current one.
	 *
	 * @param predicate must not be {@literal null}.
	 * @return
	 * @see Stream#filter(Predicate)
	 */
	default IStreamable<T> filter(Predicate<? super T> predicate) {

		assert predicate != null: "Filter predicate must not be null!";

		return IStreamable.of(() -> stream().filter(predicate));
	}

	/**
	 * Returns whether the current {@link IStreamable} is empty.
	 * 
	 * @return
	 */
	default boolean isEmpty() {
		return !iterator().hasNext();
	}

	/**
	 * Creates a new {@link IStreamable} from the current one and the given {@link Stream} concatenated.
	 * 
	 * @param stream must not be {@literal null}.
	 * @return
	 * @since 2.1
	 */
	default IStreamable<T> and(Supplier<? extends Stream<? extends T>> stream) {

		assert stream != null: "Stream must not be null!";

		return IStreamable.of(() -> Stream.concat(this.stream(), stream.get()));
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.function.Supplier#get()
	 */
	default Stream<T> get() {
		return stream();
	}
}