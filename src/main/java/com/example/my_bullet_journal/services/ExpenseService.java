package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.models.view.ExpenseViewModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface ExpenseService {

    List<ExpenseEnum> getCategories();

    //todo fix the return type
    void save(ExpenseServiceModel map);

    //todo fix it to service model
    List<ExpenseViewModel> getAllExpenses();

    BigDecimal getTotalAmountOfExpenses();

    HashMap<String, BigDecimal> expensesByCategory();


}
