package org.amdatu.tutorial.todo.jpa.dao.impl;

import javax.persistence.EntityManager;

import org.amdatu.tutorial.todo.api.TenantContext;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.apache.felix.dm.annotation.api.AspectService;
import org.hibernate.Session;

@AspectService(ranking = 1, added="added", removed="removed", service=TodoDao.class)
public class TodoDaoImplAspect {
	public void added(TodoDao dao) {
		EntityManager em = dao.getEm();
	    org.hibernate.Filter filter = em.unwrap(Session.class).enableFilter("tenantFilter");
	    filter.setParameter("tenantId", TenantContext.getCurrentTenant());
	    filter.validate();
	}
	public void removed	(TodoDao dao) {
		EntityManager em = dao.getEm();
	    em.unwrap(Session.class).disableFilter("tenantFilter");
	}
}
