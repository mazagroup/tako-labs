package org.tako.aop.support;

import java.lang.reflect.Method;

import org.tako.aop.MethodMatcher;


/**
 * Convenient abstract superclass for static method matchers, which don't care
 * about arguments at runtime.
 *
 */
public abstract class StaticMethodMatcher implements MethodMatcher {

	@Override
	public final boolean isRuntime() {
		return false;
	}

	@Override
	public final boolean matches(Method method, Class<?> targetClass, Object... args) {
		// should never be invoked because isRuntime() returns false
		throw new UnsupportedOperationException("Illegal MethodMatcher usage");
	}

}
