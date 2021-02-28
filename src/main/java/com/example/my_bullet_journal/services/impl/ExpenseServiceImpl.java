package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.entities.Expense;
import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.repositories.ExpenseRepository;
import com.example.my_bullet_journal.services.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        //todo: get the currently login user and delete userRepo from here;
        this.expenseRepository.save(expense);

    }
}
