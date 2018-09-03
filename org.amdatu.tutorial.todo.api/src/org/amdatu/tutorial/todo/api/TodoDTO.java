package org.amdatu.tutorial.todo.api;

import java.util.ArrayList;
import java.util.List;

public class TodoDTO extends TenantSupportDTO {

	public Long id = 0L;
    public String description;
    public boolean completed;
    public String user;
    
    public List<ReminderDTO> reminders = new ArrayList<>();

    public TodoDTO() {
    }

	public TodoDTO(String description, boolean completed, String user, List<ReminderDTO> reminders) {
		super();
		this.description = description;
		this.completed = completed;
		this.user = user;
		this.reminders = reminders;
	}
}
