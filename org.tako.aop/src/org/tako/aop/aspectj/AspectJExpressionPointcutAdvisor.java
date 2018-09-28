package org.tako.aop.aspectj;

import org.tako.aop.Pointcut;
import org.tako.aop.support.AbstractGenericPointcutAdvisor;
import org.tako.commons.lang.Nullable;

/**
 * Spring AOP Advisor that can be used for any AspectJ pointcut expression.
 *
 * @since 2.0
 */
@SuppressWarnings("serial")
public class AspectJExpressionPointcutAdvisor extends AbstractGenericPointcutAdvisor {

	private final AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();


	public void setExpression(@Nullable String expression) {
		this.pointcut.setExpression(expression);
	}

	@Nullable
	public String getExpression() {
		return this.pointcut.getExpression();
	}

	public void setLocation(@Nullable String location) {
		this.pointcut.setLocation(location);
	}

	@Nullable
	public String getLocation() {
		return this.pointcut.getLocation();
	}

	public void setParameterNames(String... names) {
		this.pointcut.setParameterNames(names);
	}

	public void setParameterTypes(Class<?>... types) {
		this.pointcut.setParameterTypes(types);
	}


	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

}
