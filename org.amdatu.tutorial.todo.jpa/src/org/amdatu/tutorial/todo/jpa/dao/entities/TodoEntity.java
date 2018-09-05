package org.amdatu.tutorial.todo.jpa.dao.entities;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.amdatu.tutorial.todo.api.TenantSupport;
import org.amdatu.tutorial.todo.api.TodoDTO;


@Entity
@Table(name="todos")
public class TodoEntity implements TenantSupport {

    @OneToMany(mappedBy="todo", cascade=CascadeType.ALL)
    private List<ReminderEntity> reminders = new ArrayList<>();
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    private String tenantId = "N/A";
    
    public String description;
    public boolean completed;
    public String user;
    
    
    
    public Long getId() {
		return id;
	}

	public  static TodoEntity fromDTO(TodoDTO dto) {
    	TodoEntity entity = new TodoEntity();
    	
        if(dto.id != null && dto.id != 0) {
            entity.id = Long.valueOf(dto.id);
        }
        entity.tenantId = dto.tenantId;
        
        entity.description = dto.description;
        entity.completed = dto.completed;
        entity.user = dto.user;
        entity.reminders = dto.reminders.stream()
                .map(a -> ReminderEntity.fromDTO(entity, a))
                .collect(toList());
        
        return entity;
    }
    
    public TodoDTO toDTO() {
    	TodoDTO dto = new TodoDTO();
    	
        dto.id = id;
        dto.tenantId = tenantId;
        dto.description = description;
        dto.completed = completed;
        dto.user = user;
        dto.reminders = reminders.stream()
                .map(ReminderEntity::toDTO)
                .collect(toList());
        return dto;
    }

	@Override
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}