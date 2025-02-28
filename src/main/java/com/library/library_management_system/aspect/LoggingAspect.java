/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author 123
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Define pointcuts for book and patron operations
    @Pointcut("execution(* com.tst.springbootproject.service.BookService.*(..))")
    public void bookServiceMethods() {}

    @Pointcut("execution(* com.tst.springbootproject.service.PatronService.*(..))")
    public void patronServiceMethods() {}

    @Pointcut("execution(* com.tst.springbootproject.service.BorrowingService.*(..))")
    public void borrowingServiceMethods() {}

    // Log method calls, execution time, and exceptions
    @Around("bookServiceMethods() || patronServiceMethods() || borrowingServiceMethods()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Log method entry
        logger.info("Entering method: {}.{}", className, methodName);

        long startTime = System.currentTimeMillis();

        try {
            // Proceed with the method execution
            Object result = joinPoint.proceed();

            // Log method exit and execution time
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Exiting method: {}.{} | Execution time: {} ms", className, methodName, executionTime);

            return result;
        } catch (Exception ex) {
            // Log exceptions
            logger.error("Exception in method: {}.{} | Error: {}", className, methodName, ex.getMessage(), ex);
            throw ex; // Re-throw the exception
        }
    }
}
