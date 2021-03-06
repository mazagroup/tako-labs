package org.amdatu.tutorial.todo.jpa.dao.impl;

import static java.util.stream.Collectors.toList;

import java.sql.SQLException;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.amdatu.tutorial.todo.api.ReminderDTO;
import org.amdatu.tutorial.todo.api.ReminderDao;
import org.amdatu.tutorial.todo.jpa.dao.entities.ReminderEntity;
import org.amdatu.tutorial.todo.jpa.dao.entities.TodoEntity;
import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;
import org.osgi.service.transaction.control.TransactionControl;
import org.osgi.service.transaction.control.jpa.JPAEntityManagerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(provides=ReminderDao.class)
public class ReminderDaoImpl implements ReminderDao {

    private static final Logger logger = LoggerFactory.getLogger(ReminderDaoImpl.class);

	@ServiceDependency(required=true)
	TransactionControl transactionControl;

	@ServiceDependency(name="microservice.database",required=true)
	JPAEntityManagerProvider jpaEntityManagerProvider;

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@Start
	void activate() throws SQLException {
		em = jpaEntityManagerProvider.getResource(transactionControl);
	}

	@Override
	public List<ReminderDTO> select(Long personId) {

		return transactionControl.notSupported(() -> {

		    CriteriaBuilder builder = em.getCriteriaBuilder();
		    
		    CriteriaQuery<ReminderEntity> query = builder.createQuery(ReminderEntity.class);
		    
		    Root<ReminderEntity> from = query.from(ReminderEntity.class);
		    
		    query.where(builder.equal(from.get("person").get("personId"), personId));
		    
		    return em.createQuery(query).getResultList().stream()
		            .map(ReminderEntity::toDTO)
		            .collect(toList());
		});
	}

	@Override
	public ReminderDTO findByPK(Long pk) {

		return transactionControl.notSupported(() -> {
		    ReminderEntity address = em.find(ReminderEntity.class, pk);
			return address == null ? null : address.toDTO();
		});
	}

	@Override
	public void save(Long todoId, ReminderDTO data) {
	    
	    transactionControl.required(() -> {
	    	TodoEntity todo = em.find(TodoEntity.class, todoId);
	        if(todo == null) {
	            throw new IllegalArgumentException("There is no todo with id " + todoId);
	        }
	        em.persist(ReminderEntity.fromDTO(todo, data));
	        
	        return null;
	    });
	}

	@Override
	public void update(Long todoId, ReminderDTO data) {
	    
	    transactionControl.required(() -> {
	        
	        ReminderEntity reminder = em.find(ReminderEntity.class, data.id);
	        if(reminder == null) {
                throw new IllegalArgumentException("There is no reminder with id " + data.id);
            }
	        
	        reminder.setDate(data.date);
	        
	        logger.info("Updated Reminder : {}", data);
	        
	        return null;
	    });
	}

	@Override
	public void delete(Long todoId) {
	    
		transactionControl.required(() -> {
		    CriteriaBuilder builder = em.getCriteriaBuilder();
            
            CriteriaDelete<ReminderEntity> query = builder.createCriteriaDelete(ReminderEntity.class);
            
            Root<ReminderEntity> from = query.from(ReminderEntity.class);
            
            query.where(builder.equal(from.get("todo").get("id"), todoId));
            
            em.createQuery(query).executeUpdate();
            
            return null;
		});
	}
	
}