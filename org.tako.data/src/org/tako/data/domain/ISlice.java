package org.tako.data.domain;

import java.util.List;
import java.util.function.Function;

import org.tako.data.util.Streamable;

/**
 * A ISlice of data that indicates whether there's a next or previous ISlice available. Allows to obtain a
 * {@link IPageable} to request a previous or next {@link ISlice}.
 *
 * @since 1.8
 */
public interface ISlice<T> extends Streamable<T> {

	/**
	 * Returns the number of the current {@link ISlice}. Is always non-negative.
	 *
	 * @return the number of the current {@link ISlice}.
	 */
	int getNumber();

	/**
	 * Returns the size of the {@link ISlice}.
	 *
	 * @return the size of the {@link ISlice}.
	 */
	int getSize();

	/**
	 * Returns the number of elements currently on this {@link ISlice}.
	 *
	 * @return the number of elements currently on this {@link ISlice}.
	 */
	int getNumberOfElements();

	/**
	 * Returns the page content as {@link List}.
	 *
	 * @return
	 */
	List<T> getContent();

	/**
	 * Returns whether the {@link ISlice} has content at all.
	 *
	 * @return
	 */
	boolean hasContent();

	/**
	 * Returns the sorting parameters for the {@link ISlice}.
	 *
	 * @return
	 */
	Sort getSort();

	/**
	 * Returns whether the current {@link ISlice} is the first one.
	 *
	 * @return
	 */
	boolean isFirst();

	/**
	 * Returns whether the current {@link ISlice} is the last one.
	 *
	 * @return
	 */
	boolean isLast();

	/**
	 * Returns if there is a next {@link ISlice}.
	 *
	 * @return if there is a next {@link ISlice}.
	 */
	boolean hasNext();

	/**
	 * Returns if there is a previous {@link ISlice}.
	 *
	 * @return if there is a previous {@link ISlice}.
	 */
	boolean hasPrevious();

	/**
	 * Returns the {@link IPageable} that's been used to request the current {@link ISlice}.
	 *
	 * @return
	 * @since 2.0
	 */
	default IPageable getIPageable() {
		return PageRequest.of(getNumber(), getSize(), getSort());
	}

	/**
	 * Returns the {@link IPageable} to request the next {@link ISlice}. Can be {@literal null} in case the current
	 * {@link ISlice} is already the last one. Clients should check {@link #hasNext()} before calling this method to make
	 * sure they receive a non-{@literal null} value.
	 *
	 * @return
	 */
	IPageable nextIPageable();

	/**
	 * Returns the {@link IPageable} to request the previous {@link ISlice}. Can be {@literal null} in case the current
	 * {@link ISlice} is already the first one. Clients should check {@link #hasPrevious()} before calling this method make
	 * sure receive a non-{@literal null} value.
	 *
	 * @return
	 */
	IPageable previousIPageable();

	/**
	 * Returns a new {@link ISlice} with the content of the current one mapped by the given {@link Converter}.
	 *
	 * @param converter must not be {@literal null}.
	 * @return a new {@link ISlice} with the content of the current one mapped by the given {@link Converter}.
	 * @since 1.10
	 */
	<U> ISlice<U> map(Function<? super T, ? extends U> converter);
}
