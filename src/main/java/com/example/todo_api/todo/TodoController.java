package com.example.todo_api.todo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    private final TodoRepository repository;

    private final TodoModelAssembler assembler;

    public TodoController(TodoRepository repository, TodoModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/todos")
    CollectionModel<EntityModel<Todo>> all() {
        List<EntityModel<Todo>> todos = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(todos);
    }

    @GetMapping("/todos/{id}")
    EntityModel<Todo> one(@PathVariable Long id) {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new TodoNotFountException(id));

        return assembler.toModel(todo);
    }

    @PostMapping("/todos")
    ResponseEntity<?> newTodo(@RequestBody Todo newTodo) {
        EntityModel<Todo> entityModel = assembler.toModel(repository.save(newTodo));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
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

        EntityModel<Todo> entityModel = assembler.toModel(updatedTodo);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/todos/{id}")
    ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
