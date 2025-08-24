package com.circuitbaba.todo.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Document("tasks")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String id;

    @TextIndexed(weight = 5)
    @Indexed
    private String title;

    @TextIndexed
    private String description;

    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private Set<String> tags;
    private String userId; // owner of the task

    private Instant completedAt;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;


    @Version
    private Long version; // optimistic locking
}
