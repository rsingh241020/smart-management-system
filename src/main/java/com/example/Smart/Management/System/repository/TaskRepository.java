package com.example.Smart.Management.System.repository;

import com.example.Smart.Management.System.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    // ✅ MY TASKS
    List<Task> findByAssignedTo(String assignedTo);

    // ✅ LOAD PROJECT ALSO
    @Query("""
        SELECT t
        FROM Task t
        LEFT JOIN FETCH t.project
    """)
    List<Task> findAllWithProject();
}