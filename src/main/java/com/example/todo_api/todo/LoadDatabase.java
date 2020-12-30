package com.example.todo_api.todo;

import com.example.todo_api.user.User;
import com.example.todo_api.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(TodoRepository todoRepository, UserRepository userRepository) {
        return args -> {
            todoRepository.save(new Todo("sample1", "description1", new Date()));
            todoRepository.save(new Todo("sample2", "description2", new Date()));
            userRepository.save(new User("name1", new Date()));
            userRepository.save(new User("name2", new Date()));
        };
    }
}
