package com.example.Smart.Management.System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String createdBy;

    // ✅ ONE PROJECT → MANY TASKS
    @OneToMany(mappedBy = "project")

    @JsonIgnore
    private List<Task> tasks;
}