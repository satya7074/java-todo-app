package com.circuitbaba.todo.service;

import com.circuitbaba.todo.dto.PageResponse;
import com.circuitbaba.todo.dto.TaskCreateRequest;
import com.circuitbaba.todo.dto.TaskResponse;
import com.circuitbaba.todo.dto.TaskUpdateRequest;
import com.circuitbaba.todo.entity.Task;
import com.circuitbaba.todo.exception.ForbiddenActionException;
import com.circuitbaba.todo.exception.ResourceNotFoundException;
import com.circuitbaba.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repo;


    @Override
    public TaskResponse create(TaskCreateRequest req, Authentication auth) {
        Task task = Task.builder()
                .title(req.title())
                .description(req.description())
                .status(req.status())
                .priority(req.priority())
                .dueDate(req.dueDate())
                .tags(req.tags())
                .userId(auth.getName())
                .build();
// completedAt if created as DONE
        if (task.getStatus() != null && task.getStatus().name().equals("DONE")) {
            task.setCompletedAt(Instant.now());
        }
        Task saved = repo.save(task);
        return toResponse(saved);
    }


    @Override
    public PageResponse<TaskResponse> list(int page, int size, String sort, String dir, String q,Authentication auth) {
        Sort s = (StringUtils.hasText(sort) ? Sort.by(Sort.Direction.fromOptionalString(dir).orElse(Sort.Direction.DESC), sort)
                : Sort.by(Sort.Direction.DESC, "createdAt"));
        Pageable pageable = PageRequest.of(page, size, s);


        Page<Task> p;
        if (StringUtils.hasText(q)) {
            p = repo.findByTitleRegexIgnoreCase(".*" + q + ".*", pageable);
        } else {
            p = repo.findAll(pageable);
        }
        return new PageResponse<>(
                p.map(this::toResponse).getContent(),
                p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages(), p.isFirst(), p.isLast()
        );
    }


    @Override
    public TaskResponse getById(String id, Authentication auth) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (isAdmin(auth)) {
            return toResponse(task); // admin can only view
        }

        if (!task.getUserId().equals(auth.getName())) {
            throw new ResourceNotFoundException("Task not found");
        }
        return toResponse(task);
    }


    @Override
    public TaskResponse update(String id, TaskUpdateRequest req, Authentication auth) {
        if (isAdmin(auth)) {
            throw new ForbiddenActionException("Admins are not allowed to update tasks");
        }

        Task task = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getUserId().equals(auth.getName())) {
            throw new ResourceNotFoundException("Task not found");
        }

        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setPriority(req.priority());
        task.setDueDate(req.dueDate());
        task.setTags(req.tags());
        if (req.status() != null) {
            task.setStatus(req.status());
            if (req.status().name().equals("DONE")) {
                task.setCompletedAt(Instant.now());
            } else {
                task.setCompletedAt(null);
            }

            task.setCompletedAt(req.completedAt());
            task.setUpdatedAt(Instant.now());
        }
        return toResponse(repo.save(task));
    }

    @Override
    public void delete(String id, Authentication auth) {
        if (isAdmin(auth)) {
            throw new ForbiddenActionException("Admins are not allowed to delete tasks");
        }
        Task task = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (!task.getUserId().equals(auth.getName())) {
            throw new ResourceNotFoundException("Task not found");
        }
        repo.delete(task);
    }

    private TaskResponse toResponse(Task t) {
        return new TaskResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus(),
                t.getPriority(),
                t.getDueDate(),
                t.getTags(),
                t.getUserId(),
                t.getCompletedAt(),
                t.getCreatedAt(),
                t.getUpdatedAt(),
                t.getVersion()
        );
    }

    @Override
    public List<TaskResponse> listAll(Authentication auth) {
        if (isAdmin(auth)) {
            return repo.findAll().stream().map(this::toResponse).toList();
        }
        return repo.findByUserId(auth.getName())
                .stream().map(this::toResponse).toList();
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }
}