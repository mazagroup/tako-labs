package org.tako.aop.aspectj;

import org.tako.commons.core.Ordered;
import org.tako.commons.lang.Nullable;

/**
 * Interface implemented to provide an instance of an AspectJ aspect.
 * Decouples from Spring's bean factory.
 *
 * <p>Extends the {@link org.tako.commons.core.Ordered} interface
 * to express an order value for the underlying aspect in a chain.
 *
 * @since 2.0
 * @see org.springframework.beans.factory.BeanFactory#getBean
 */
public interface AspectInstanceFactory extends Ordered {

	/**
	 * Create an instance of this factory's aspect.
	 * @return the aspect instance (never {@code null})
	 */
	Object getAspectInstance();

	/**
	 * Expose the aspect class loader that this factory uses.
	 * @return the aspect class loader (or {@code null} for the bootstrap loader)
	 * @see org.tako.commons.util.ClassUtils#getDefaultClassLoader()
	 */
	@Nullable
	ClassLoader getAspectClassLoader();

}
