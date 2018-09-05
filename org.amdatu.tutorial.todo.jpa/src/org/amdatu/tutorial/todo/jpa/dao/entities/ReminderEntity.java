package org.amdatu.tutorial.todo.jpa.dao.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.amdatu.tutorial.todo.api.ReminderDTO;
import org.amdatu.tutorial.todo.api.TenantSupport;


@Entity
@Table(name="reminders")
public class ReminderEntity implements TenantSupport {

    @ManyToOne
    private TodoEntity todo;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    private String tenantId = "N/A#2";
    
    private Date date;
    
    public static ReminderEntity fromDTO(TodoEntity todo, ReminderDTO dto) {
    	ReminderEntity entity = new ReminderEntity();
    	
        entity.todo = todo;
        entity.date = dto.date;
        entity.tenantId = dto.tenantId;
        
        return entity;
    }
    
    public ReminderDTO toDTO() {
    	ReminderDTO dto = new ReminderDTO();
    	
        dto.todo_id = todo.getId();
        dto.id = id;
        dto.date = date;
        dto.tenantId = tenantId;
        
        return dto;
    }

	public void setDate(Date date) {
		this.date = date;
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