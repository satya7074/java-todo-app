package com.circuitbaba.todo.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenActionException extends RuntimeException {
    public ForbiddenActionException(String message) {
        super(message);
    }
}

