package org.amdatu.tutorial.todo.api;
import java.util.List;

public interface TodoService {

    List<TodoDTO> list(String user);

    void store(TodoDTO todo);

}