package com.circuitbaba.todo.dto;

import com.circuitbaba.todo.entity.Priority;
import com.circuitbaba.todo.entity.Status;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record TaskUpdateRequest(
        @Size(max = 120)
        String title,
        @Size(max = 2000)
        String description,
        Status status,
        Priority priority,
        LocalDate dueDate,
        Set<@Pattern(regexp = "^[a-zA-Z0-9_-]{1,30}$") String> tags,
        String userId,
        Instant completedAt
) {}