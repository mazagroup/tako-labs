package org.amdatu.tutorial.todo.jpa.dao.impl;


import org.amdatu.tutorial.todo.api.TenantContext;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

@Aspect
public class TodoDaoAspect {
	
	@Around("execution(* org.amdatu.tutorial.todo.api.TodoDao.*(..)) && !execution(* org.amdatu.tutorial.todo.api.TodoDao.getTC(..)) && !execution(* org.amdatu.tutorial.todo.api.TodoDao.getEm(..)) && target(todoService)")
	public Object aroundExecution(ProceedingJoinPoint joinPoint, TodoDao todoService) throws Throwable {
		Object res = todoService.getTC().required(() -> {
	  	  	  org.hibernate.Filter filter = todoService.getEm().unwrap(Session.class).enableFilter("tenantFilter");
	  		  filter.setParameter("tenantId", TenantContext.getCurrentTenant());
	  		  filter.validate();
	  		  Object res1 = null;
	  		   try {
		  			res1 = joinPoint.proceed();
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
	  		   return res1;
	      	});
		return res;
	}
}