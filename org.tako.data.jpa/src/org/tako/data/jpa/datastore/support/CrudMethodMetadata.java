package org.tako.data.jpa.datastore.support;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.Nullable;

/**
 * Interface to abstract {@link CrudMethodMetadata} that provide the {@link LockModeType} to be used for query
 * execution.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 * @author Mark Paluch
 */
public interface CrudMethodMetadata {

	/**
	 * Returns the {@link LockModeType} to be used.
	 *
	 * @return
	 */
	@Nullable
	LockModeType getLockModeType();

	/**
	 * Returns all query hints to be applied to queries executed for the CRUD method.
	 *
	 * @return
	 */
	Map<String, Object> getQueryHints();

	/**
	 * Returns the {@link EntityGraph} to be used.
	 *
	 * @return
	 * @since 1.9
	 */
	Optional<EntityGraph> getEntityGraph();

	/**
	 * Returns the {@link Method} to be used.
	 *
	 * @return
	 * @since 1.9
	 */
	Method getMethod();
}