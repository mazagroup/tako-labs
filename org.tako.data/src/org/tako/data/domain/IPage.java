package org.tako.data.domain;

import java.util.Collections;
import java.util.function.Function;

/**
 * A page is a sublist of a list of objects. It allows gain information about the position of it in the containing
 * entire list.
 */
public interface IPage<T> extends ISlice<T> {

	/**
	 * Creates a new empty {@link Page}.
	 *
	 * @return
	 * @since 2.0
	 */
	static <T> IPage<T> empty() {
		return empty(IPageable.unpaged());
	}

	/**
	 * Creates a new empty {@link Page} for the given {@link Pageable}.
	 *
	 * @param pageable must not be {@literal null}.
	 * @return
	 * @since 2.0
	 */
	static <T> IPage<T> empty(IPageable pageable) {
		return new PageImpl<>(Collections.emptyList(), pageable, 0);
	}

	/**
	 * Returns the number of total pages.
	 *
	 * @return the number of total pages
	 */
	int getTotalPages();

	/**
	 * Returns the total amount of elements.
	 *
	 * @return the total amount of elements
	 */
	long getTotalElements();

	/**
	 * Returns a new {@link Page} with the content of the current one mapped by the given {@link Function}.
	 *
	 * @param converter must not be {@literal null}.
	 * @return a new {@link Page} with the content of the current one mapped by the given {@link Function}.
	 * @since 1.10
	 */
	<U> IPage<U> map(Function<? super T, ? extends U> converter);
}
