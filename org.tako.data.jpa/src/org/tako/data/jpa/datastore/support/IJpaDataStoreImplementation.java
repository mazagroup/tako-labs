package org.tako.data.jpa.datastore.support;

import org.tako.data.jpa.datastore.IJPADataStore;
import org.tako.data.jpa.datastore.IJpaSpecificationExecutor;

public interface IJpaDataStoreImplementation<T, ID> extends IJPADataStore<T, ID>, IJpaSpecificationExecutor<T> {

	/**
	 * Configures the {@link CrudMethodMetadata} to be used with the repository.
	 *
	 * @param crudMethodMetadata must not be {@literal null}.
	 */
	void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata);
}
