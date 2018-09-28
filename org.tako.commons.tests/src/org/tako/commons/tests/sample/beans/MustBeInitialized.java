package org.tako.commons.tests.sample.beans;


/**
 * Simple test of BeanFactory initialization
 * @since 12.03.2003
 */
public class MustBeInitialized {

	private boolean inited;

	/**
	 * Dummy business method that will fail unless the factory
	 * managed the bean's lifecycle correctly
	 */
	public void businessMethod() {
		if (!this.inited)
			throw new RuntimeException("Factory didn't call afterPropertiesSet() on MustBeInitialized object");
	}

}
