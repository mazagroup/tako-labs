package org.tako.data.commons.datastore;

import org.tako.data.commons.domain.IPage;
import org.tako.data.commons.domain.IPageable;
import org.tako.data.commons.domain.Sort;

public interface IPagingAndSortingDataStore<T, ID> extends ICrudDataStore<T, ID> {

	/**
	 * Returns all entities sorted by the given options.
	 *
	 * @param sort
	 * @return all entities sorted by the given options
	 */
	Iterable<T> findAll(Sort sort);

	/**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
	 *
	 * @param pageable
	 * @return a page of entities
	 */
	IPage<T> findAll(IPageable pageable);
}