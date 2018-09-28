package org.tako.data.jpa.datastore;

import java.util.List;
import java.util.Optional;

import org.tako.data.domain.IPage;
import org.tako.data.domain.IPageable;
import org.tako.data.domain.Sort;
import org.tako.data.jpa.domain.ISpecification;


/**
 * Interface to allow execution of {@link ISpecification}s based on the JPA criteria API.
 *
 */
public interface IJpaSpecificationExecutor<T> {

	/**
	 * Returns a single entity matching the given {@link ISpecification} or {@link Optional#empty()} if none found.
	 *
	 * @param spec can be {@literal null}.
	 * @return never {@literal null}.
	 * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if more than one entity found.
	 */
	Optional<T> findOne(ISpecification<T> spec);

	/**
	 * Returns all entities matching the given {@link ISpecification}.
	 *
	 * @param spec can be {@literal null}.
	 * @return never {@literal null}.
	 */
	List<T> findAll(ISpecification<T> spec);

	/**
	 * Returns a {@link Page} of entities matching the given {@link ISpecification}.
	 *
	 * @param spec can be {@literal null}.
	 * @param pageable must not be {@literal null}.
	 * @return never {@literal null}.
	 */
	IPage<T> findAll(ISpecification<T> spec, IPageable pageable);

	/**
	 * Returns all entities matching the given {@link ISpecification} and {@link Sort}.
	 *
	 * @param spec can be {@literal null}.
	 * @param sort must not be {@literal null}.
	 * @return never {@literal null}.
	 */
	List<T> findAll(ISpecification<T> spec, Sort sort);

	/**
	 * Returns the number of instances that the given {@link ISpecification} will return.
	 *
	 * @param spec the {@link ISpecification} to count instances for. Can be {@literal null}.
	 * @return the number of instances.
	 */
	long count(ISpecification<T> spec);
}
