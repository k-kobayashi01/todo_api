package com.example.todo_api.todo;

public class TodoNotFountException extends RuntimeException {

    public TodoNotFountException(Long id) {
        super("Could not find todo " + id);
    }
}
