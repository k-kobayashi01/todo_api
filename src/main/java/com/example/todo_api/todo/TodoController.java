package com.example.todo_api.todo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    CollectionModel<EntityModel<Todo>> all() {
        List<EntityModel<Todo>> todos = repository.findAll().stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(todos);
    }

    private EntityModel<Todo> toModel(Todo todo) {
        return EntityModel.of(todo);
    }
}
