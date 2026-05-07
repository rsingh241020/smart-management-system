package com.example.Smart.Management.System.controller;

import com.example.Smart.Management.System.model.Task;
import com.example.Smart.Management.System.model.TaskStatus;
import com.example.Smart.Management.System.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 🔴 ADMIN ONLY → Create Task
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }

    // 🔴 ADMIN ONLY → View all tasks
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Task> getAll() {
        return taskService.getAll();
    }

    // 🟢 ADMIN + MEMBER → Update status
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id,
                             @RequestParam String status,
                             HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        return taskService.updateStatus(id, status, email);
    }

    // 🟢 ADMIN + MEMBER → View own tasks
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/my")
    public List<Task> myTasks(HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        return taskService.getByUser(email);
    }

    // 🟢 ADMIN + MEMBER → Overdue tasks
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/overdue")
    public List<Task> overdue() {
        return taskService.getOverdue();
    }

    // 🔥 DASHBOARD SUMMARY
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @GetMapping("/summary")
    public Map<String, Long> summary(HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        List<Task> tasks = taskService.getByUser(email);

        long total = tasks.size();
        long completed = tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE)
                .count();

        long pending = tasks.stream()
                .filter(t -> t.getStatus() != TaskStatus.DONE)
                .count();

        return Map.of(
                "total", total,
                "completed", completed,
                "pending", pending
        );
    }
}