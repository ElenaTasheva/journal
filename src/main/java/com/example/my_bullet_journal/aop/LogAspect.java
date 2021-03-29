package com.example.my_bullet_journal.aop;

import com.example.my_bullet_journal.models.entities.LogExceptionEntity;
import com.example.my_bullet_journal.services.LogService;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }



    @AfterThrowing(pointcut = "execution(* com.example.my_bullet_journal.services.*.*(..))", throwing = "error")
    public void afterAdvice(JoinPoint joinPoint, Throwable error){

        LogExceptionEntity logExceptionEntity = new LogExceptionEntity();
        logExceptionEntity.setMessage(error.getMessage());
        this.logService.saveLog(logExceptionEntity);
    }
}
