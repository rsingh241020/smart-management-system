package com.example.Smart.Management.System.controller;

import com.example.Smart.Management.System.model.Project;
import com.example.Smart.Management.System.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Project create(@RequestBody Project project, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        project.setCreatedBy(email);
        return projectService.create(project);
    }

    @GetMapping
    public List<Project> getAll() {
        return projectService.getAll();
    }
}
