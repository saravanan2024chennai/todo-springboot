package com.stackroute.ansibledemo.controller;

import model.Todo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private List<Todo> todos = new ArrayList<>();
    private Long nextId = 1L;

    public TodoController() {
        todos.add(new Todo(nextId++, "Learn Spring Boot", false));
        todos.add(new Todo(nextId++, "Learn Ansible", false));
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todos;
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        todo.setId(nextId++);
        todos.add(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Optional<Todo> existingTodo = todos.stream().filter(todo -> todo.getId().equals(id)).findFirst();
        if (existingTodo.isPresent()) {
            existingTodo.get().setTask(updatedTodo.getTask());
            existingTodo.get().setCompleted(updatedTodo.isCompleted());
            return existingTodo.get();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public boolean deleteTodo(@PathVariable Long id) {
        return todos.removeIf(todo -> todo.getId().equals(id));
    }
}