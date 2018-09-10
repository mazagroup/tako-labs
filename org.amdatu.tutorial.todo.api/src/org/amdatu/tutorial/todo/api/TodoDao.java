package org.amdatu.tutorial.todo.api;

import java.util.List;

import javax.persistence.EntityManager;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.transaction.control.TransactionControl;

@ProviderType
public interface TodoDao {
    
    public List<TodoDTO> select();
    
	public List<TodoDTO> selectByDescription(String description);

    public TodoDTO findByPK(Long pk) ;

    public Long save(TodoDTO data);

    public void update(TodoDTO data);

    public void delete(Long pk) ;

	public void enableTenantFilter(String tenantId);

	public TransactionControl getTC();

	public EntityManager getEm();
}