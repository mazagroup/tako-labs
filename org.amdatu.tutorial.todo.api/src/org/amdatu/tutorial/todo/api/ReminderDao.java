package org.amdatu.tutorial.todo.api;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface ReminderDao {
    
    public List<ReminderDTO> select(Long pk);

    public ReminderDTO findByPK(Long pk);

    public void save(Long todoId,ReminderDTO data);

    public void update(Long todoId,ReminderDTO data);

    public void delete(Long todoId) ;

}
