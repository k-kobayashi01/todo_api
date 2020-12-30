package com.example.todo_api.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(TodoRepository todoRepository) {
        return args -> {
            todoRepository.save(new Todo("sample1", "description1", new Date()));
            todoRepository.save(new Todo("sample2", "description2", new Date()));
        };
    }
}
