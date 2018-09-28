package org.tako.data.domain;

import java.util.Optional;

public interface IPageable {

	/**
	 * Returns a {@link IPageable} instance representing no pagination setup.
	 *
	 * @return
	 */
	static IPageable unpaged() {
		return IUnpaged.INSTANCE;
	}

	/**
	 * Returns whether the current {@link IPageable} contains pagination information.
	 *
	 * @return
	 */
	default boolean isPaged() {
		return true;
	}

	/**
	 * Returns whether the current {@link IPageable} does not contain pagination information.
	 *
	 * @return
	 */
	default boolean isUnpaged() {
		return !isPaged();
	}

	/**
	 * Returns the page to be returned.
	 *
	 * @return the page to be returned.
	 */
	int getPageNumber();

	/**
	 * Returns the number of items to be returned.
	 *
	 * @return the number of items of that page
	 */
	int getPageSize();

	/**
	 * Returns the offset to be taken according to the underlying page and page size.
	 *
	 * @return the offset to be taken
	 */
	long getOffset();

	/**
	 * Returns the sorting parameters.
	 *
	 * @return
	 */
	Sort getSort();

	/**
	 * Returns the current {@link Sort} or the given one if the current one is unsorted.
	 *
	 * @param sort must not be {@literal null}.
	 * @return
	 */
	default Sort getSortOr(Sort sort) {

		assert sort != null: "Fallback Sort must not be null!";

		return getSort().isSorted() ? getSort() : sort;
	}

	/**
	 * Returns the {@link IPageable} requesting the next {@link Page}.
	 *
	 * @return
	 */
	IPageable next();

	/**
	 * Returns the previous {@link IPageable} or the first {@link IPageable} if the current one already is the first one.
	 *
	 * @return
	 */
	IPageable previousOrFirst();

	/**
	 * Returns the {@link IPageable} requesting the first page.
	 *
	 * @return
	 */
	IPageable first();

	/**
	 * Returns whether there's a previous {@link IPageable} we can access from the current one. Will return
	 * {@literal false} in case the current {@link IPageable} already refers to the first page.
	 *
	 * @return
	 */
	boolean hasPrevious();

	/**
	 * Returns an {@link Optional} so that it can easily be mapped on.
	 *
	 * @return
	 */
	default Optional<IPageable> toOptional() {
		return isUnpaged() ? Optional.empty() : Optional.of(this);
	}
}
