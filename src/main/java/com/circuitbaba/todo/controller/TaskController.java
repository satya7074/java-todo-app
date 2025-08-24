package com.circuitbaba.todo.controller;

import com.circuitbaba.todo.dto.TaskCreateRequest;
import com.circuitbaba.todo.dto.TaskResponse;
import com.circuitbaba.todo.dto.TaskUpdateRequest;
import com.circuitbaba.todo.service.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Log4j2
public class TaskController {

    private final TaskServiceImpl service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody TaskCreateRequest req, Authentication authentication) {
        return service.create(req, authentication);
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable String id, Authentication authentication) {
        return service.getById(id,authentication);
    }

    @Operation(summary = "Returns a Hello World message")
    @GetMapping("/taskList")
    public List<TaskResponse> list(Authentication authentication) {
        return service.listAll(authentication);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable String id,
                               @Valid @RequestBody TaskUpdateRequest req, Authentication authentication) {
        return service.update(id, req,authentication);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String delete(@PathVariable String id, Authentication authentication) {
        service.delete(id,authentication);
        return "Task Deleted successfully.";
    }
}

