package org.tako.aop.support;

import org.tako.aop.Pointcut;
import org.tako.commons.lang.Nullable;

/**
 * Interface to be implemented by pointcuts that use String expressions.
 *
 * @since 2.0
 */
public interface ExpressionPointcut extends Pointcut {

	/**
	 * Return the String expression for this pointcut.
	 */
	@Nullable
	String getExpression();

}
