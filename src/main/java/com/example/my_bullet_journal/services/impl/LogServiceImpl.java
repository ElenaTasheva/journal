package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.entities.LogExceptionEntity;
import com.example.my_bullet_journal.repositories.LogRepository;
import com.example.my_bullet_journal.services.LogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    @Override
    public void saveLog(LogExceptionEntity exceptionEntity) {
        this.logRepository.save(exceptionEntity);
    }


    @Scheduled(cron = "0 0 0 * * *")
    protected void deleteErrors(){
        this.logRepository.deleteAll();
    }
}
