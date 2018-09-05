package org.amdatu.tutorial.todo.jpa.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.amdatu.tutorial.todo.api.TenantContext;
import org.amdatu.tutorial.todo.api.TenantSupport;
import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.type.Type;

@SuppressWarnings("serial")
public class EntityPersistenceStateInterceptor extends EmptyInterceptor 	{
	
    public EntityPersistenceStateInterceptor() {
		super();
	}

	private static final Logger logger = LoggerFactory.getLogger(EntityPersistenceStateInterceptor.class);
    
    
    
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
      if (entity instanceof TenantSupport) {
    	logger.info("[save] Updating the entity " + id + " with util information: " + TenantContext.getCurrentTenant());
        ((TenantSupport) entity).setTenantId(TenantContext.getCurrentTenant());
        
        return true;
      }
      return super.onSave(entity,  id, state, propertyNames, types);
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
        return true;
      }
      return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
    
    @Override
    public void preFlush(Iterator entities) {
    	while (entities.hasNext()) {
    	     Object entity = entities.next();
			if (entity  instanceof TenantSupport) {
    	     	logger.info("[preFlush]");
    	         ((TenantSupport) entity).setTenantId(TenantContext.getCurrentTenant());
    	       }	
    	}
    }
}
