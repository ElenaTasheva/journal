package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.LogExceptionEntity;

public interface LogService {

    void saveLog(LogExceptionEntity exceptionEntity);
}
