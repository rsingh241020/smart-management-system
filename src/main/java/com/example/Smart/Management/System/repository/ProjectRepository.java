package com.example.Smart.Management.System.repository;

import com.example.Smart.Management.System.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}