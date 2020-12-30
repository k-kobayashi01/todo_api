package com.example.todo_api.todo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TodoModelAssembler implements RepresentationModelAssembler<Todo, EntityModel<Todo>> {

    @Override
    public EntityModel<Todo> toModel(Todo entity) {
        return EntityModel.of(entity, linkTo(methodOn(TodoController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(TodoController.class).all()).withRel("todos"));
    }
}
