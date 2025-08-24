package com.circuitbaba.todo.repository;

import com.circuitbaba.todo.entity.Status;
import com.circuitbaba.todo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    Page<Task> findByStatus(Status status, Pageable pageable);
    Page<Task> findByDueDateBefore(LocalDate date, Pageable pageable);
    Page<Task> findByTitleRegexIgnoreCase(String regex, Pageable pageable);

    List<Task> findByUserId(String name);
}
