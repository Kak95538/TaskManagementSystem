package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.entity.Role;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskStatus;
import com.example.demo.entity.User;
import com.example.demo.dto.TaskDTO;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    // Create Task using TaskDTO
    public Task createTask(TaskDTO dto) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        log.info("Fetching user from DB: {}", username);

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new RuntimeException("User not found");
                });

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setUser(user);
        task.setCompleted(false);

        log.info("Creating task '{}' for user '{}'", dto.getTitle(), username);

        Task savedTask = taskRepo.save(task);

        log.info("Task saved with ID: {}", savedTask.getId());

        return savedTask;
    }
    // List Tasks for a user
//    public List<Task> getTasks(Long userId) {
//        return taskRepo.findByUserId(userId);
//    }
    public List<Task> getTasksByUsername(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepo.findByUserId(user.getId());
    }

    // Update task by ID using TaskDTO
    public Task updateTask(Long taskId, TaskDTO dto) {
        Task task = taskRepo.findById(taskId)
                     .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        return taskRepo.save(task);
    }
    
    // Delete task by ID
    public void deleteTask(Long taskId, String username) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔐 SECURITY CHECK
        if (user.getRole() == Role.USER) {
            // User can delete only their own task
            if (!task.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized to delete this task");
            }
        }

        taskRepo.delete(task);
    }
    
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
    
    public Task markComplete(Long taskId, String username) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔐 SECURITY CHECK
        if (user.getRole() == Role.USER) {
            if (!task.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Not allowed to complete this task");
            }
        }

        // ✅ mark status
        task.setCompleted(true);

        return taskRepo.save(task);
    }
    public Task updateTask(Long taskId, TaskDTO dto, String username) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  SECURITY CHECK
        if (user.getRole() == Role.USER) {
            // User can update only their own task
            if (!task.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized to update this task");
            }
        }

        //  Update fields
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        return taskRepo.save(task);
    }
    
    public List<Task> getCompletedTasks(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            return taskRepo.findByCompleted(true);
        }

        return taskRepo.findByUserIdAndCompleted(user.getId(), true);
    }
    
    public Map<String, Long> getTaskCount(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Long> response = new HashMap<>();

        if (user.getRole() == Role.ADMIN) {

            response.put("total", taskRepo.count());
            response.put("completed", taskRepo.countByCompleted(true));
            response.put("pending", taskRepo.countByCompleted(false));

        } else {

            response.put("total", taskRepo.countByUserId(user.getId()));
            response.put("completed", taskRepo.countByUserIdAndCompleted(user.getId(), true));
            response.put("pending", taskRepo.countByUserIdAndCompleted(user.getId(), false));
        }

        return response;
    }
    
    public Page<Task> getTasks(int page, int size, String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PageRequest pageable = PageRequest.of(page, size);

        if (user.getRole() == Role.ADMIN) {
            return taskRepo.findAll(pageable);
        }

        return taskRepo.findByUserId(user.getId(), pageable);
    }
    
}