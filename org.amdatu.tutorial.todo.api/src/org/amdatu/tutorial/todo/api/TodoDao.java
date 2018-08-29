package org.amdatu.tutorial.todo.api;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface TodoDao {
    
    public List<TodoDTO> select();

    public TodoDTO findByPK(Long pk) ;

    public Long save(TodoDTO data);

    public void update(TodoDTO data);

    public void delete(Long pk) ;
}