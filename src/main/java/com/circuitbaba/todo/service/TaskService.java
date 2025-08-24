package com.circuitbaba.todo.service;

import com.circuitbaba.todo.dto.PageResponse;
import com.circuitbaba.todo.dto.TaskCreateRequest;
import com.circuitbaba.todo.dto.TaskResponse;
import com.circuitbaba.todo.dto.TaskUpdateRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TaskService {
    TaskResponse create(TaskCreateRequest req, Authentication authentication);
    PageResponse<TaskResponse> list(int page, int size, String sort, String dir, String q, Authentication authentication);
    TaskResponse getById(String id, Authentication authentication);
    TaskResponse update(String id, TaskUpdateRequest req,Authentication authentication);
    void delete(String id, Authentication authentication);
    List<TaskResponse> listAll(Authentication authentication);
}
