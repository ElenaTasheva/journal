package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.entities.Expense;
import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.models.view.ExpenseViewModel;
import com.example.my_bullet_journal.repositories.ExpenseRepository;
import com.example.my_bullet_journal.services.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;


    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ExpenseEnum> getCategories(){
       return Arrays.stream(ExpenseEnum.values()).collect(Collectors.toList());
    }

    @Override
    public void save(ExpenseServiceModel expenseServiceModel) {

        Expense expense = this.modelMapper.map(expenseServiceModel, Expense.class);
        //todo: get the currently login user and delete userRepo from here and from Income;
        this.expenseRepository.save(expense);

    }

    @Override
    public List<ExpenseViewModel> getAllExpenses() {
       return this.expenseRepository.findAllAndOrderByCategory()
                .stream()
                .map(expense -> this.modelMapper.map(expense, ExpenseViewModel.class))
                .collect(Collectors.toList());
       //todo fix it if its empty;
    }

    @Override
    public BigDecimal getTotalAmountOfExpenses() {
        return this.expenseRepository.getTotal();
    }

    @Override
    public HashMap<String, BigDecimal> expensesByCategory() {
        HashMap<String, BigDecimal> result = new HashMap<>();
        List<Object[]> list = this.expenseRepository.finExpensesByCategories();
        for (Object[] ob: list) {
            String key = (String)ob[0];
            BigDecimal value = (BigDecimal) ob[1];
            result.put(key, value);
            
        }
        return result;
    }
}
