package org.tako.aop.framework.autoproxy;

import org.tako.commons.core.NamedThreadLocal;
import org.tako.commons.lang.Nullable;

/**
 * Holder for the current proxy creation context, as exposed by auto-proxy creators
 * such as {@link AbstractAdvisorAutoProxyCreator}.
 *
 * @since 2.5
 */
public final class ProxyCreationContext {

	/** ThreadLocal holding the current proxied bean name during Advisor matching. */
	private static final ThreadLocal<String> currentProxiedBeanName =
			new NamedThreadLocal<>("Name of currently proxied bean");


	private ProxyCreationContext() {
	}


	/**
	 * Return the name of the currently proxied bean instance.
	 * @return the name of the bean, or {@code null} if none available
	 */
	@Nullable
	public static String getCurrentProxiedBeanName() {
		return currentProxiedBeanName.get();
	}

	/**
	 * Set the name of the currently proxied bean instance.
	 * @param beanName the name of the bean, or {@code null} to reset it
	 */
	static void setCurrentProxiedBeanName(@Nullable String beanName) {
		if (beanName != null) {
			currentProxiedBeanName.set(beanName);
		}
		else {
			currentProxiedBeanName.remove();
		}
	}

}
