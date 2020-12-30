package com.example.todo_api.todo;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Todo {

    private @Id @GeneratedValue Long id;
    private String title;
    private String description;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date;

    public Todo() {}

    public Todo(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
