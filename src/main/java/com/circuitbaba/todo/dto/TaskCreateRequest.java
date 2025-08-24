package com.circuitbaba.todo.dto;

import com.circuitbaba.todo.entity.Priority;
import com.circuitbaba.todo.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record TaskCreateRequest(
        @NotBlank @Size(max = 120) String title,
        @Size(max = 2000) String description,
        @NotNull Status status,
        @NotNull Priority priority,
        LocalDate dueDate,
        Set<String> tags
) {}