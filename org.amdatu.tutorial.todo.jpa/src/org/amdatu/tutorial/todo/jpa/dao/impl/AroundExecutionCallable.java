package org.amdatu.tutorial.todo.jpa.dao.impl;

import java.util.concurrent.Callable;

import org.amdatu.tutorial.todo.api.TenantContext;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Session;

public class AroundExecutionCallable implements Callable<Object> {
	  private ProceedingJoinPoint joinPoint;
	  private TodoDao todoService;
	  public AroundExecutionCallable(ProceedingJoinPoint joinPoint, TodoDao todoService) {
	    this.joinPoint = joinPoint;
	    this.todoService = todoService;
	  }
	  @Override
	  public Integer call() {
		  org.hibernate.Filter filter = todoService.getEm().unwrap(Session.class).enableFilter("tenantFilter");
		  filter.setParameter("tenantId", TenantContext.getCurrentTenant());
		  filter.validate();
		  Object res1 = null;
		   try {
	  			res1 = joinPoint.proceed();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		   return 0;
	  }
	}