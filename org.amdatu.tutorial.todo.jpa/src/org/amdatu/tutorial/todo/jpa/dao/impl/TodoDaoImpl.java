package org.amdatu.tutorial.todo.jpa.dao.impl;

import static java.util.stream.Collectors.toList;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.amdatu.tutorial.todo.api.TodoDTO;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.amdatu.tutorial.todo.jpa.dao.entities.TodoEntity;
import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;
import org.hibernate.Session;
import org.osgi.service.transaction.control.TransactionControl;
import org.osgi.service.transaction.control.jpa.JPAEntityManagerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(provides=TodoDao.class)
public class TodoDaoImpl implements TodoDao {
    
    private static final Logger logger = LoggerFactory.getLogger(TodoDaoImpl.class);

    @ServiceDependency(required=true)
    TransactionControl transactionControl;

    @ServiceDependency(name="microservice.database",required=true)
    JPAEntityManagerProvider jpaEntityManagerProvider;

    private EntityManager em;
    


    @Override
    public EntityManager getEm() {
		return em;
	}
    

	@Override
	public TransactionControl getTC() {
		return transactionControl;
	}
    

	@Start
    void activate() throws SQLException {
        em = jpaEntityManagerProvider.getResource(transactionControl);
    }

    @Override
    public List<TodoDTO> select() {

        //return transactionControl.requiresNew(() -> {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            
            CriteriaQuery<TodoEntity> query = builder.createQuery(TodoEntity.class);
            
            query.from(TodoEntity.class);
            
            return em.createQuery(query).getResultList().stream()
                    .map(TodoEntity::toDTO)
                    .collect(toList());
        //});
    }
    
	@Override 
	public List<TodoDTO> selectByDescription(String description) {
		CriteriaBuilder builder = getEm().getCriteriaBuilder();
		
		ParameterExpression<String> dType = builder.parameter(String.class);
		
		CriteriaBuilder qb =  getEm().getCriteriaBuilder();
		CriteriaQuery<TodoEntity> cq = qb.createQuery(TodoEntity.class);
		Root<TodoEntity> rootEntity = cq.from(TodoEntity.class);
		cq.select(rootEntity).where(builder.and(
				builder.equal(rootEntity.get("description"),dType)
				));
		
		TypedQuery<TodoEntity> typedQuery = getEm().createQuery(cq);
		typedQuery.setParameter(dType, description);
		
        return typedQuery.getResultList().stream()
                .map(TodoEntity::toDTO)
                .collect(toList());	
	}    

    @Override
    public void delete(Long primaryKey) {

        transactionControl.required(() -> {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            
            CriteriaDelete<TodoEntity> query = builder.createCriteriaDelete(TodoEntity.class);
            
            Root<TodoEntity> from = query.from(TodoEntity.class);
            
            query.where(builder.equal(from.get("id"), primaryKey));
            
            em.createQuery(query).executeUpdate();
            
            logger.info("Deleted ToDo with ID : {}", primaryKey);
            return null;
        });
    }

    @Override
    public TodoDTO findByPK(Long pk) {

       return transactionControl.notSupported(() -> {
    	   TodoEntity person = em.find(TodoEntity.class, pk);
           return person == null ? null : person.toDTO();
        });
    }

    @Override
    public Long save(TodoDTO data) {

        return transactionControl.required(() -> {

            TodoEntity entity = TodoEntity.fromDTO(data);
            
            if(entity.getId() == null) {
                em.persist(entity);
            } else {
                em.merge(entity);
            }

            logger.info("Saved Todo with ID : {}", entity.getId());

            return entity.getId();
        });
    }

    @Override
    public void update(TodoDTO data) {

        transactionControl.required(() -> {

        	TodoEntity entity = TodoEntity.fromDTO(data);
            
            if(entity.getId() <= 0) {
                throw new IllegalStateException("No primary key defined for the Entity");
            } else {
                em.merge(entity);
            }

            logger.info("Updated person : {}", data);

            return null;
        });
    }
    
    
    @Override
    public void enableTenantFilter(String tenantId) {
    	transactionControl.required(() -> {
	  	  org.hibernate.Filter filter = getEm().unwrap(Session.class).enableFilter("tenantFilter");
		  filter.setParameter("tenantId", tenantId);
		  filter.validate();
		  
		  return null;
    	});
    }
    
}

