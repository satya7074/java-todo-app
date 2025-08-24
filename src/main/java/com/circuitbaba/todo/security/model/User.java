package com.circuitbaba.todo.security.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String password;  // will be encoded
    private String role;      // e.g., ROLE_USER, ROLE_ADMIN
}

