package org.tako.tests.aop.interceptor;

import org.tako.aop.support.DelegatingIntroductionInterceptor;
import org.tako.commons.tests.TimeStamped;

@SuppressWarnings("serial")
public class TimestampIntroductionInterceptor extends DelegatingIntroductionInterceptor
		implements TimeStamped {

	private long ts;

	public TimestampIntroductionInterceptor() {
	}

	public TimestampIntroductionInterceptor(long ts) {
		this.ts = ts;
	}

	public void setTime(long ts) {
		this.ts = ts;
	}

	@Override
	public long getTimeStamp() {
		return ts;
	}

}
