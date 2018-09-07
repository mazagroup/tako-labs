package org.amdatu.tutorial.todo.jpa.dao.impl;


import org.amdatu.tutorial.todo.api.TenantContext;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;

@Aspect
public class TodoDaoAspect {

	@Before("execution(* org.amdatu.tutorial.todo.api.TodoDao.*(..)) && !execution(* org.amdatu.tutorial.todo.api.TodoDao.getEm(..)) && target(todoService)")
	public void aroundExecution(TodoDao todoService) throws Throwable {
	  org.hibernate.Filter filter = todoService.getEm().unwrap(Session.class).enableFilter("tenantFilter");
	  filter.setParameter("tenantId", TenantContext.getCurrentTenant());
	  filter.validate();
	}
}