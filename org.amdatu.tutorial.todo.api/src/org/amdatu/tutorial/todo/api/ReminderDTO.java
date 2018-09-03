package org.amdatu.tutorial.todo.api;

import java.util.Date;

public class ReminderDTO extends TenantSupportDTO {

    public Long id = 0L;
    public Date date;
    public Long todo_id;

    public ReminderDTO() {
    }

	public ReminderDTO(Date date) {
		super();
		this.date = date;
	}
    
}
