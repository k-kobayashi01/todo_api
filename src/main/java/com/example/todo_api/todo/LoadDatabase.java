package com.example.todo_api.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(TodoRepository todoRepository) {
        return args -> {
            todoRepository.save(new Todo("sample1", "description1", LocalDateTime.now()));
            todoRepository.save(new Todo("sample2", "description2", LocalDateTime.now()));
        };
    }
}
