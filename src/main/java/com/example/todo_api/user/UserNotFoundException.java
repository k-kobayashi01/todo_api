package com.example.todo_api.user;

public class UserNotFoundException extends RuntimeException {

    public  UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
