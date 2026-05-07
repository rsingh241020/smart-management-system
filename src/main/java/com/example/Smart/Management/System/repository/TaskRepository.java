package com.example.Smart.Management.System.repository;


import com.example.Smart.Management.System.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}