package org.tako.data.jpa.datastore.query;

import org.tako.data.datastore.core.EntityMetadata;

/**
 * JPA specific extension of {@link EntityMetadata}.
 *
 */
public interface JpaEntityMetadata<T> extends EntityMetadata<T> {

	/**
	 * Returns the name of the entity.
	 *
	 * @return
	 */
	String getEntityName();
}
