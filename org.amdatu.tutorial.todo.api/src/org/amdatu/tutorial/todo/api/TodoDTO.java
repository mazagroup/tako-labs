package org.amdatu.tutorial.todo.api;

import java.util.ArrayList;
import java.util.List;

public class TodoDTO {

	public Long id;
    public String description;
    public boolean completed;
    public String user;
    
    public List<ReminderDTO> reminders = new ArrayList<>();

    public TodoDTO() {
    }
}
