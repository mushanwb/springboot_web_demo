package com.mushanwb.github.entity;

import java.time.Instant;

public class User {
    private Integer id;
    private String username;
    private String avatar;
    private String encryptPassword;
    private Instant createdAt;
    private Instant updatedAt;

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
        this.avatar = "";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
