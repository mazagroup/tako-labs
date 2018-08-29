package org.amdatu.tutorial.todo.jpa.dao.impl;

import static java.util.stream.Collectors.toList;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.amdatu.tutorial.todo.api.TodoDTO;
import org.amdatu.tutorial.todo.api.TodoDao;
import org.amdatu.tutorial.todo.jpa.dao.entities.TodoEntity;
import org.osgi.service.transaction.control.TransactionControl;
import org.osgi.service.transaction.control.jpa.JPAEntityManagerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class TodoDaoImpl implements TodoDao {
    
    private static final Logger logger = LoggerFactory.getLogger(TodoDaoImpl.class);

    @Reference
    TransactionControl transactionControl;

    @Reference(name="provider")
    JPAEntityManagerProvider jpaEntityManagerProvider;

    EntityManager em;

    @Activate
    void activate(Map<String, Object> props) throws SQLException {
        em = jpaEntityManagerProvider.getResource(transactionControl);
    }

    @Override
    public List<TodoDTO> select() {

        return transactionControl.notSupported(() -> {

            CriteriaBuilder builder = em.getCriteriaBuilder();
            
            CriteriaQuery<TodoEntity> query = builder.createQuery(TodoEntity.class);
            
            query.from(TodoEntity.class);
            
            return em.createQuery(query).getResultList().stream()
                    .map(TodoEntity::toDTO)
                    .collect(toList());
        });
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

       return transactionControl.supports(() -> {
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

            logger.info("Saved Person with ID : {}", entity.getId());

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
}

