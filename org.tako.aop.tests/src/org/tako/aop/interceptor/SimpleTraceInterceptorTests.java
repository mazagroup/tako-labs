package org.tako.aop.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.tako.aop.interceptor.SimpleTraceInterceptor;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

/**
 * Unit tests for the {@link SimpleTraceInterceptor} class.
 *
 */
@RunWith(JUnit4.class)
public class SimpleTraceInterceptorTests {

	@Test
	public void testSunnyDayPathLogsCorrectly() throws Throwable {
		MethodInvocation mi = mock(MethodInvocation.class);
		given(mi.getMethod()).willReturn(String.class.getMethod("toString"));
		given(mi.getThis()).willReturn(this);

		Logger log = mock(Logger.class);

		SimpleTraceInterceptor interceptor = new SimpleTraceInterceptor(true);
		interceptor.invokeUnderTrace(mi, log);

		verify(log, times(2)).trace(anyString());
	}

	@Test
	public void testExceptionPathStillLogsCorrectly() throws Throwable {
		MethodInvocation mi = mock(MethodInvocation.class);
		given(mi.getMethod()).willReturn(String.class.getMethod("toString"));
		given(mi.getThis()).willReturn(this);
		IllegalArgumentException exception = new IllegalArgumentException();
		given(mi.proceed()).willThrow(exception);

		Logger log = mock(Logger.class);

		final SimpleTraceInterceptor interceptor = new SimpleTraceInterceptor(true);

		try {
			interceptor.invokeUnderTrace(mi, log);
			fail("Must have propagated the IllegalArgumentException.");
		}
		catch (IllegalArgumentException expected) {
		}

		verify(log).trace(anyString());
		verify(log).trace(anyString(), eq(exception));
	}

}
