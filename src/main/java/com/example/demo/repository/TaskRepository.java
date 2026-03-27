package com.example.demo.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByCompleted(boolean completed);

    List<Task> findByUserIdAndCompleted(Long userId, boolean completed);
    
    long countByCompleted(boolean completed);

    long countByUserId(Long userId);

    long countByUserIdAndCompleted(Long userId, boolean completed);
    
    Page<Task> findByUserId(Long userId, Pageable pageable);
    
}
