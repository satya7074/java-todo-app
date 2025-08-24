package com.circuitbaba.todo.dto;

import com.circuitbaba.todo.entity.Priority;
import com.circuitbaba.todo.entity.Status;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record TaskResponse(
        String id,
        String title,
        String description,
        Status status,
        Priority priority,
        LocalDate dueDate,
        Set<String> tags,
        String userId,
        Instant completedAt,
        Instant createdAt,
        Instant updatedAt,
        Long version
) {}
