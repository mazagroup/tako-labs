package org.tako.data.datastore.core;

/**
 * Metadata for entity types.
 */
public interface EntityMetadata<T> {

	/**
	 * Returns the actual domain class type.
	 *
	 * @return
	 */
	Class<T> getJavaType();
}
