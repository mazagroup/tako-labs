package org.amdatu.tutorial.weaving.explicit;

import java.text.MessageFormat;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AfterAspectJ {

	@After("execution (* org.amdatu.tutorial.weaving.Application.methodToModify(..))")
	public void methodAfter(JoinPoint joinPoint) {
		String signature = joinPoint.getSignature().getName();
		String joinedArgs = "";
		for (Object arg : joinPoint.getArgs()) {
			joinedArgs.concat(",").concat(arg.toString());
		}
		System.err.println(MessageFormat.format("AspectJ - " + joinPoint.getKind() + " - @After  method <{0}>, args: <{1}> - no arguments accessible - do something after the method", signature, joinedArgs)); //$NON-NLS-1$
	}
}
