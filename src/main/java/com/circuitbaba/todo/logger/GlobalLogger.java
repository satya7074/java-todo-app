package com.circuitbaba.todo.logger;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class GlobalLogger {

    @Before("execution(* com.circuitbaba.todo.service..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering: {}", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.circuitbaba.todo.service..*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("Exiting: {} with result = {}", joinPoint.getSignature(), result);
    }
    @Before("execution(* com.circuitbaba.todo.security..*(..))")
    public void logBeforeSecurity(JoinPoint joinPoint) {
        log.info("Entering: {}", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.circuitbaba.todo.security..*(..))", returning = "result")
    public void logAfterSecurity(JoinPoint joinPoint, Object result) {
        log.info("Exiting: {} with result = {}", joinPoint.getSignature(), result);
    }
}
