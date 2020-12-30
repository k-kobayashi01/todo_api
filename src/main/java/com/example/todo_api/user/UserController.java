package com.example.todo_api.user;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository repository;

    private final UserModelAssembler assembler;

    public UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(users);
    }

    @GetMapping("/users/{id}")
    EntityModel<User> one(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }

    @PostMapping("/users")
    ResponseEntity<?> newUsers(@RequestBody User user) {
        final EntityModel<User> entityModel = assembler.toModel(repository.save(user));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}
