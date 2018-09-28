package org.tako.commons.tests.sample.beans;


/**
 * Simple test of BeanFactory initialization and lifecycle callbacks.
 *
 * @since 12.03.2003
 */
public class LifecycleBean  {

	protected boolean initMethodDeclared = false;

	protected String beanName;

	protected boolean postProcessedBeforeInit;

	protected boolean inited;

	protected boolean initedViaDeclaredInitMethod;

	protected boolean postProcessedAfterInit;

	protected boolean destroyed;


	public void setInitMethodDeclared(boolean initMethodDeclared) {
		this.initMethodDeclared = initMethodDeclared;
	}

	public boolean isInitMethodDeclared() {
		return initMethodDeclared;
	}

	public void setBeanName(String name) {
		this.beanName = name;
	}

	public String getBeanName() {
		return beanName;
	}


	public void postProcessBeforeInit() {
		if (this.inited || this.initedViaDeclaredInitMethod) {
			throw new RuntimeException("Factory called postProcessBeforeInit after afterPropertiesSet");
		}
		if (this.postProcessedBeforeInit) {
			throw new RuntimeException("Factory called postProcessBeforeInit twice");
		}
		this.postProcessedBeforeInit = true;
	}


	public void declaredInitMethod() {
		if (!this.inited) {
			throw new RuntimeException("Factory didn't call afterPropertiesSet before declared init method");
		}

		if (this.initedViaDeclaredInitMethod) {
			throw new RuntimeException("Factory called declared init method twice");
		}
		this.initedViaDeclaredInitMethod = true;
	}

	public void postProcessAfterInit() {
		if (!this.inited) {
			throw new RuntimeException("Factory called postProcessAfterInit before afterPropertiesSet");
		}
		if (this.initMethodDeclared && !this.initedViaDeclaredInitMethod) {
			throw new RuntimeException("Factory called postProcessAfterInit before calling declared init method");
		}
		if (this.postProcessedAfterInit) {
			throw new RuntimeException("Factory called postProcessAfterInit twice");
		}
		this.postProcessedAfterInit = true;
	}

	/**
	 * Dummy business method that will fail unless the factory
	 * managed the bean's lifecycle correctly
	 */
	public void businessMethod() {
		if (!this.inited || (this.initMethodDeclared && !this.initedViaDeclaredInitMethod) ||
				!this.postProcessedAfterInit) {
			throw new RuntimeException("Factory didn't initialize lifecycle object correctly");
		}
	}

	public void destroy() {
		if (this.destroyed) {
			throw new IllegalStateException("Already destroyed");
		}
		this.destroyed = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}


}
