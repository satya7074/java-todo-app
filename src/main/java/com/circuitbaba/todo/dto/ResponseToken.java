package com.circuitbaba.todo.dto;

import lombok.Builder;

@Builder
public record ResponseToken(String token) {
}
