package org.amdatu.tutorial.todo.api;
public class Todo {

    private String id;
    private String description;
    private boolean completed;
    private String user;

    public Todo() {

    }

    public Todo(String description, String user) {
        this.id = null;
        this.description = description;
        this.user = user;
        this.completed = false;
    }


    public Todo(String id, String description, String user, boolean completed) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
