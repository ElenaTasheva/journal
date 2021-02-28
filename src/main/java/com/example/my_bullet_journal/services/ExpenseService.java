package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;

import java.util.List;

public interface ExpenseService {

    List<ExpenseEnum> getCategories();

    //todo fix the return type
    void save(ExpenseServiceModel map);
}
