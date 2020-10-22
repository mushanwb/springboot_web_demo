package com.mushanwb.github.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class User {
    private Integer id;
    private String username;
    private String avatar;
    @JsonIgnore
    private String encryptPassword;
    private Instant createdAt;
    private Instant updatedAt;

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
        this.avatar = "https://offerhkok.oss-cn-shenzhen.aliyuncs.com/service_table/8462020-07-17-05-58-24.png";
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
