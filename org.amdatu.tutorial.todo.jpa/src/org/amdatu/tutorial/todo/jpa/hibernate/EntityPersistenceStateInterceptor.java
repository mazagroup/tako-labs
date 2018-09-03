package org.amdatu.tutorial.todo.jpa.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.amdatu.tutorial.todo.api.TenantContext;
import org.amdatu.tutorial.todo.api.TenantSupport;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.type.Type;

@SuppressWarnings("serial")
public class EntityPersistenceStateInterceptor implements Interceptor 	{
	
    public EntityPersistenceStateInterceptor() {
		super();
	}

	private static final Logger logger = LoggerFactory.getLogger(EntityPersistenceStateInterceptor.class);
    
    
    
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
      if (entity instanceof TenantSupport) {
    	logger.info("[save] Updating the entity " + id + " with util information: " + TenantContext.getCurrentTenant());
        ((TenantSupport) entity).setTenantId(TenantContext.getCurrentTenant());
      }
      return false;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
      if (entity instanceof TenantSupport) {
    	logger.info("[delete] Updating the entity " + id + " with util information: " + TenantContext.getCurrentTenant());
        ((TenantSupport) entity).setTenantId(TenantContext.getCurrentTenant());
      }
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
      if (entity instanceof TenantSupport) {
    	logger.info("[flush-dirty] Updating the entity " + id + " with util information: " + TenantContext.getCurrentTenant());
        ((TenantSupport) entity).setTenantId(TenantContext.getCurrentTenant());
      }
      return false;
    }

	@Override
	public void afterTransactionBegin(Transaction arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTransactionCompletion(Transaction arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTransactionCompletion(Transaction arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] findDirty(Object arg0, Serializable arg1, Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getEntity(String arg0, Serializable arg1) throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEntityName(Object arg0) throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object instantiate(String arg0, EntityMode arg1, Serializable arg2) throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isTransient(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCollectionRecreate(Object arg0, Serializable arg1) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollectionRemove(Object arg0, Serializable arg1) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollectionUpdate(Object arg0, Serializable arg1) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onLoad(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4)
			throws CallbackException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String onPrepareStatement(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postFlush(Iterator arg0) throws CallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preFlush(Iterator arg0) throws CallbackException {
		// TODO Auto-generated method stub
		
	}
}
