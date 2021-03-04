package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.Income;
import com.example.my_bullet_journal.models.enums.IncomeEnum;
import com.example.my_bullet_journal.models.services.IncomeServiceModel;
import com.example.my_bullet_journal.models.view.IncomeViewModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface IncomeService {
    List<IncomeEnum> getCategories();

    void save(IncomeServiceModel incomeServiceModel);

    List<IncomeViewModel> getAllIncome();

    BigDecimal getTotalIncome();

    HashMap<String, BigDecimal> incomeByCategory();
}
