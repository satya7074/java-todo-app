package com.circuitbaba.todo.dataseed;

import com.circuitbaba.todo.entity.Priority;
import com.circuitbaba.todo.entity.Status;
import com.circuitbaba.todo.entity.Task;
import com.circuitbaba.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Log4j2
public class DataSeeder implements CommandLineRunner {

    private final TaskRepository repo;

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            Task t1 = Task.builder()
                    .title("Learn Spring Boot + MongoDB")
                    .description("Build a ToDoApp backend")
                    .status(Status.PENDING)
                    .priority(Priority.valueOf("HIGH"))
                    .dueDate(LocalDate.now().plusDays(3))
                    .tags(Set.of("spring", "backend"))
                    .build();

            Task t2 = Task.builder()
                    .title("Build React Vite frontend")
                    .description("Consume Spring Boot APIs")
                    .status(Status.PENDING)
                    .priority(Priority.valueOf("MEDIUM"))
                    .dueDate(LocalDate.now().plusDays(7))
                    .tags(Set.of("react", "frontend"))
                    .build();

            repo.saveAll(List.of(t1, t2));
            System.out.println("✅ Sample tasks seeded into MongoDB");
        }
    }
}

