package com.nabil.managers.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<File> files;

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public List<File> getFiles(){
        return files;
    }

    public String getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFiles(List<File> files){
        this.files = files;
    }
}
