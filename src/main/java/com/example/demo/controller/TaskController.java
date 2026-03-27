package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String deleteTask(@PathVariable Long id,
                             Authentication auth) {

        String username = auth.getName();
        taskService.deleteTask(id, username);

        return "Task deleted successfully";
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto) {

        log.info("Incoming request to create task: {}", dto.getTitle());

        Task createdTask = taskService.createTask(dto);

        log.info("Task created successfully with id: {}", createdTask.getId());

        return ResponseEntity.ok(createdTask);
    }
    
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public String deleteTask(@PathVariable Long id) {
//        taskService.deleteTask(id);
//        return "Task deleted successfully";
//    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> adminTasks() {
        return taskService.getAllTasks();
    }

//    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER')")
//    public List<Task> userTasks(@RequestParam Long userId) {
//        return taskService.getTasks(userId);
//    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public List<Task> userTasks(Authentication auth) {

        String username = auth.getName();
        return taskService.getTasksByUsername(username);
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody TaskDTO dto,
                           Authentication auth) {

        String username = auth.getName(); // from JWT
        return taskService.updateTask(id, dto, username);
    }
    
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Task completeTask(@PathVariable Long id,
                             Authentication auth) {

        String username = auth.getName();
        return taskService.markComplete(id, username);
    }
    
    @GetMapping("/completed")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Task> getCompletedTasks(Authentication auth) {

        String username = auth.getName();
        return taskService.getCompletedTasks(username);
    }
    
    @GetMapping("/count")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String, Long> getTaskCount(Authentication auth) {

        String username = auth.getName();
        return taskService.getTaskCount(username);
    }
    
    @GetMapping("/all")
    public Page<Task> getTasks(
            @RequestParam int page,
            @RequestParam int size,
            Authentication auth) {

        return taskService.getTasks(page, size, auth.getName());
    }
    
}