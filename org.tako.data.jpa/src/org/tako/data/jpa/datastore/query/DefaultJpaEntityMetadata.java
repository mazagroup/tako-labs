package org.tako.data.jpa.datastore.query;

import javax.persistence.Entity;

import org.tako.data.commons.util.Assert;
import org.tako.data.commons.util.StringUtils;
import org.tako.data.core.annotation.AnnotatedElementUtils;


/**
 * Default implementation for {@link JpaEntityMetadata}.
 */
public class DefaultJpaEntityMetadata<T> implements JpaEntityMetadata<T> {

	private final Class<T> domainType;

	/**
	 * Creates a new {@link DefaultJpaEntityMetadata} for the given domain type.
	 *
	 * @param domainType must not be {@literal null}.
	 */
	public DefaultJpaEntityMetadata(Class<T> domainType) {

		Assert.notNull(domainType, "Domain type must not be null!");
		this.domainType = domainType;
	}

	
	@Override
	public Class<T> getJavaType() {
		return domainType;
	}

	
	@Override
	public String getEntityName() {

		Entity entity = AnnotatedElementUtils.findMergedAnnotation(domainType, Entity.class);
		return null != entity && StringUtils.hasText(entity.name()) ? entity.name() : domainType.getSimpleName();
	}
}
