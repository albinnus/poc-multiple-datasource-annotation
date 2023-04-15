package com.example.poc.infra.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ReadOnlyInterception {
    @Around("@annotation(com.example.poc.infra.annotations.ReaderDataSource)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            ReadOnlyContext.enter();
            return joinPoint.proceed();
        } finally {
            ReadOnlyContext.exit();
        }
    }
}