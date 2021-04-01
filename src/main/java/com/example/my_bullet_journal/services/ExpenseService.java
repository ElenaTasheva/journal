package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.models.view.ExpenseViewModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface ExpenseService {

    List<ExpenseEnum> getCategories();

    void save(ExpenseServiceModel map, String userEmail);

    List<ExpenseViewModel> getAllExpensesOrderByCategory(String email);

    BigDecimal getTotalAmountOfExpenses(String email);

    HashMap<String, BigDecimal> expenseSumByCategory(Long userId);


}
