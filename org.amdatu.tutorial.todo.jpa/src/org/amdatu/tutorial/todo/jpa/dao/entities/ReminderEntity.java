package org.amdatu.tutorial.todo.jpa.dao.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.amdatu.tutorial.todo.api.ReminderDTO;


@Entity
@Table(name="reminders")
public class ReminderEntity {

    @ManyToOne
    @JoinColumn(name="todo_id", foreignKey=@ForeignKey(name="todo"))
    private TodoEntity todo;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    private Date date;
    
    public static ReminderEntity fromDTO(TodoEntity todo, ReminderDTO dto) {
    	ReminderEntity entity = new ReminderEntity();
        entity.todo = todo;
        entity.date = dto.date;
        
        return entity;
    }
    
    public ReminderDTO toDTO() {
    	ReminderDTO dto = new ReminderDTO();
        dto.todo_id = todo.getId();
        dto.id = id;
        dto.date = date;
        
        return dto;
    }

	public void setDate(Date date) {
		this.date = date;
	}
}