package org.amdatu.tutorial.todo.api;
import java.util.List;

public interface TodoService {

    List<Todo> list(String user);

    void store(Todo todo);

}