package org.tako.commons.tests.sample.beans;

/**
 * Bean that changes state on a business invocation, so that
 * we can check whether it's been invoked
 */
public class SideEffectBean {

	private int count;

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return this.count;
	}

	public void doWork() {
		++count;
	}

}
