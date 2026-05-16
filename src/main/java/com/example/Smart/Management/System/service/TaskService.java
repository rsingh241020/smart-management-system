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

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {

        this.taskRepository = taskRepository;

        this.userRepository = userRepository;
    }

    // =====================================
    // CREATE TASK
    // =====================================

    public Task create(Task task) {

        System.out.println(
                "Assigned To = "
                        + task.getAssignedTo()
        );

        // ✅ CHECK USER EXISTS

        userRepository.findByEmail(
                        task.getAssignedTo()
                )
                .orElseThrow(() ->

                        new RuntimeException(
                                "User not found with email: "
                                        + task.getAssignedTo()
                        )
                );

        // ✅ DEFAULT STATUS

        if (task.getStatus() == null) {

            task.setStatus(
                    TaskStatus.TODO
            );
        }

        // ✅ DEBUG

        System.out.println(task);

        return taskRepository.save(task);
    }

    // =====================================
    // GET ALL TASKS
    // =====================================

    public List<Task> getAll() {

        // ✅ LOAD PROJECT ALSO

        return taskRepository.findAllWithProject();
    }

    // =====================================
    // UPDATE STATUS
    // =====================================

    public Task updateStatus(
            Long id,
            String status,
            String email
    ) {

        Task task =
                taskRepository.findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Task not found"
                                )
                        );

        // ✅ ONLY OWN TASK UPDATE

        if (
                !task.getAssignedTo()
                        .equals(email)
        ) {

            throw new RuntimeException(
                    "You can update only your own task"
            );
        }

        // ✅ UPDATE STATUS

        task.setStatus(

                TaskStatus.valueOf(
                        status.toUpperCase()
                )
        );

        return taskRepository.save(task);
    }

    // =====================================
    // MY TASKS
    // =====================================

    public List<Task> getByUser(
            String email
    ) {

        return taskRepository
                .findByAssignedTo(email);
    }

    // =====================================
    // OVERDUE TASKS
    // =====================================

    public List<Task> getOverdue() {

        return taskRepository.findAll()

                .stream()

                .filter(task ->

                        task.getDueDate() != null

                                &&

                                task.getDueDate()
                                        .isBefore(
                                                LocalDate.now()
                                        )

                                &&

                                task.getStatus()
                                        != TaskStatus.DONE
                )

                .toList();
    }
}