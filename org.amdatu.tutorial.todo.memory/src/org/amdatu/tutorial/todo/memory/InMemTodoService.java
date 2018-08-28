package org.amdatu.tutorial.todo.memory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.amdatu.tutorial.todo.api.Todo;
import org.amdatu.tutorial.todo.api.TodoService;
import org.apache.felix.dm.annotation.api.Component;

@Component
public class InMemTodoService implements TodoService {

    private final List<Todo> todos = new CopyOnWriteArrayList<>();

    @Override
    public void store(Todo todo) {
        todos.add(todo);
    }

    @Override
    public List<Todo> list(String user) {
        return todos.stream()
            .filter(t -> t.getUser().equals(user))
            .collect(Collectors.toList());
    }

}