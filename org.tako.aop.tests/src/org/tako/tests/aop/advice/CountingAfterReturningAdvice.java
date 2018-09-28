package org.tako.tests.aop.advice;

import java.lang.reflect.Method;

import org.tako.aop.AfterReturningAdvice;

/**
 * Simple before advice example that we can use for counting checks.
 *
 */
@SuppressWarnings("serial")
public class CountingAfterReturningAdvice extends MethodCounter implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object o, Method m, Object[] args, Object target) throws Throwable {
		count(m);
	}

}
