package com.example.todo_api.todo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping("/todos/{id}")
    EntityModel<Todo> one(@PathVariable Long id) {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new TodoNotFountException(id));

        return toModel(todo);
    }

    @PostMapping("/todos")
    ResponseEntity<?> newTodo(@RequestBody Todo newTodo) {
        EntityModel<Todo> entityModel = toModel(repository.save(newTodo));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    private EntityModel<Todo> toModel(Todo todo) {
        return EntityModel.of(todo, linkTo(methodOn(TodoController.class).one(todo.getId())).withSelfRel(),
                linkTo(methodOn(TodoController.class).all()).withRel("todos"));
    }

    @PutMapping("/todos/{id}")
    ResponseEntity<?> replaceTodo(@RequestBody Todo newTodo, @PathVariable Long id) {
        Todo updatedTodo = repository.findById(id)
                .map(todo -> {
                    todo.setTitle(newTodo.getTitle());
                    todo.setDescription(newTodo.getDescription());
                    return repository.save(todo);
                })
                .orElseGet(() -> {
                    newTodo.setId(id);
                    return repository.save(newTodo);
                });

        EntityModel<Todo> entityModel = toModel(updatedTodo);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
