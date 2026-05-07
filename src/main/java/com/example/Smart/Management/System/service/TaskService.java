package com.example.Smart.Management.System.service;

import com.example.Smart.Management.System.model.Task;
import com.example.Smart.Management.System.model.TaskStatus;
import com.example.Smart.Management.System.repository.TaskRepository;
import com.example.Smart.Management.System.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // 🔴 ADMIN → Create task (with user validation)
    public Task create(Task task) {

        // ✅ Check assigned user exists
        userRepository.findByEmail(task.getAssignedTo())
                .orElseThrow(() -> new RuntimeException("User not found, cannot assign task"));

        task.setStatus(TaskStatus.TODO);

        return taskRepository.save(task);
    }

    // 🔴 ADMIN → Get all tasks
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    // 🟢 ADMIN + MEMBER → Update status
    public Task updateStatus(Long id, String status, String email) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 🔥 Only allow user to update own task
        if (task.getAssignedTo() != null && !task.getAssignedTo().equals(email)) {
            throw new RuntimeException("You can only update your own tasks");
        }

        task.setStatus(TaskStatus.valueOf(status));
        return taskRepository.save(task);
    }

    // 🟢 USER → only own tasks
    public List<Task> getByUser(String email) {
        return taskRepository.findAll()
                .stream()
                .filter(t -> t.getAssignedTo() != null && t.getAssignedTo().equals(email))
                .toList();
    }

    // ⏰ Overdue tasks
    public List<Task> getOverdue() {
        return taskRepository.findAll()
                .stream()
                .filter(t -> t.getDueDate() != null &&
                        t.getDueDate().isBefore(LocalDate.now()) &&
                        t.getStatus() != TaskStatus.DONE)
                .toList();
    }
}