package org.tako.aop.support;

import java.io.Serializable;

import org.aopalliance.aop.Advice;
import org.tako.aop.PointcutAdvisor;
import org.tako.commons.core.Ordered;
import org.tako.commons.lang.Nullable;
import org.tako.commons.util.ObjectUtils;


/**
 * Abstract base class for {@link org.springframework.aop.PointcutAdvisor}
 * implementations. Can be subclassed for returning a specific pointcut/advice
 * or a freely configurable pointcut/advice.
 *
 * @since 1.1.2
 * @see AbstractGenericPointcutAdvisor
 */
@SuppressWarnings("serial")
public abstract class AbstractPointcutAdvisor implements PointcutAdvisor, Ordered, Serializable {

	@Nullable
	private Integer order;


	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		if (this.order != null) {
			return this.order;
		}
		Advice advice = getAdvice();
		if (advice instanceof Ordered) {
			return ((Ordered) advice).getOrder();
		}
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public boolean isPerInstance() {
		return true;
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PointcutAdvisor)) {
			return false;
		}
		PointcutAdvisor otherAdvisor = (PointcutAdvisor) other;
		return (ObjectUtils.nullSafeEquals(getAdvice(), otherAdvisor.getAdvice()) &&
				ObjectUtils.nullSafeEquals(getPointcut(), otherAdvisor.getPointcut()));
	}

	@Override
	public int hashCode() {
		return PointcutAdvisor.class.hashCode();
	}

}
