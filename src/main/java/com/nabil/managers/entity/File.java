package com.nabil.managers.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    private Integer size;

    private String type;

    private LocalDateTime createdAt;

    public File() {
    }

    public File(User user, String name, Integer size, String type) {
        this.user = user;
        this.name = name;
        this.size = size;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }


    public User getUser(){
        return user;
    }

    public String getName(){
        return name;
    }

    public Integer getSize(){
        return size;
    }

    public String getType(){
        return type;
    }

    public String getCreatedAt(){
        return createdAt.toString();
    }




}
