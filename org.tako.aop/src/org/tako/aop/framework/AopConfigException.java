package org.tako.aop.framework;

import org.tako.commons.core.NestedRuntimeException;

/**
 * Exception that gets thrown on illegal AOP configuration arguments.
 *
 * @since 13.03.2003
 */
@SuppressWarnings("serial")
public class AopConfigException extends NestedRuntimeException {

	/**
	 * Constructor for AopConfigException.
	 * @param msg the detail message
	 */
	public AopConfigException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for AopConfigException.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public AopConfigException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
