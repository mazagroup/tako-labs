package org.tako.tests.aop.advice;

import org.tako.aop.support.DefaultIntroductionAdvisor;
import org.tako.aop.support.DelegatingIntroductionInterceptor;
import org.tako.tests.aop.interceptor.TimestampIntroductionInterceptor;

/**
 *
 */
@SuppressWarnings("serial")
public class TimestampIntroductionAdvisor extends DefaultIntroductionAdvisor {

	/**
	 * @param dii
	 */
	public TimestampIntroductionAdvisor() {
		super(new DelegatingIntroductionInterceptor(new TimestampIntroductionInterceptor()));
	}

}
