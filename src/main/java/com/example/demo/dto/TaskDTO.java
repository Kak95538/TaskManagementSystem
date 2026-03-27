package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    
//    private Long userId;
    
    // Getters and Setters
//    public Long getUserId() { return userId; }
//    public void setUserId(Long userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
