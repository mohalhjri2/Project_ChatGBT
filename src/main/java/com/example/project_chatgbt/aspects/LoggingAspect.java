package com.example.project_chatgbt.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut to match all methods in services
    @Pointcut("execution(* com.example.project_chatgbt.services.*.*(..))")
    public void serviceMethods() {
    }

    // Before advice
    @Before("serviceMethods()")
    public void logBeforeExecution() {
        logger.info("A method in the service layer is about to execute.");
    }

    // After advice
    @After("serviceMethods()")
    public void logAfterExecution() {
        logger.info("A method in the service layer has executed.");
    }

    // After returning advice
    @AfterReturning(value = "serviceMethods()", returning = "result")
    public void logAfterReturning(Object result) {
        logger.info("A method in the service layer executed successfully and returned: {}", result);
    }

    // After throwing advice
    @AfterThrowing(value = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        logger.error("An exception occurred in the service layer: {}", exception.getMessage(), exception);
    }

    // Around advice
    @Around("serviceMethods()")
    public Object logAroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method {} is about to execute.", joinPoint.getSignature());
        long startTime = System.currentTimeMillis();

        Object result;

        try {
            result = joinPoint.proceed(); // Proceed with the method execution
        } catch (Exception e) {
            logger.error("Exception in method {}: {}", joinPoint.getSignature(), e.getMessage(), e);
            throw e; // Rethrow the exception
        }

        long totalTime = System.currentTimeMillis() - startTime;
        logger.info("Method {} executed in {} ms and returned: {}", joinPoint.getSignature(), totalTime, result);

        return result;
    }
}
