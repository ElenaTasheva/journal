package com.example.my_bullet_journal.services.impl;


import com.example.my_bullet_journal.models.entities.Expense;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.services.ExpenseServiceModel;
import com.example.my_bullet_journal.models.view.ExpenseViewModel;
import com.example.my_bullet_journal.repositories.ExpenseRepository;
import com.example.my_bullet_journal.services.ExpenseService;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ModelMapper modelMapper, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<ExpenseEnum> getCategories(){
       return Arrays.stream(ExpenseEnum.values()).collect(Collectors.toList());
    }

    @Override
    public void save(ExpenseServiceModel expenseServiceModel, String userEmail) {

        Expense expense = this.modelMapper.map(expenseServiceModel, Expense.class);
        User user = this.userService.findByEmail(userEmail);
        expense.setUser(user);
        this.expenseRepository.save(expense);

    }

    @Override
    public List<ExpenseViewModel> getAllExpensesOrderByCategory(String email) {
        Long userId = getUserId(email);
       return this.expenseRepository.findAllAndOrderByCategory(userId)
                .stream()
                .map(expense -> this.modelMapper.map(expense, ExpenseViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public BigDecimal getTotalAmountOfExpenses(String email) {
       Long userId = getUserId(email);
       return this.expenseRepository.getTotal(userId) != null ? this.expenseRepository.getTotal(userId) : BigDecimal.valueOf(0) ;
    }



    @Override
    public HashMap<String, BigDecimal> expenseSumByCategory(Long userId) {
        HashMap<String, BigDecimal> result = new HashMap<>();
        List<Object[]> list = this.expenseRepository.finExpensesByCategories(userId);
        for (Object[] ob: list) {
            String key = (String)ob[0];
            BigDecimal value = (BigDecimal) ob[1];
            result.put(key, value);
            
        }
        return result;
    }


    private Long getUserId(String email) {
        return  this.userService.findByEmail(email).getId();
    }

    @Scheduled(cron = "0 0 0 1 * *")
    protected void changeExpenseStatusToCompleted() {
        this.expenseRepository.changeMonthlyStatus(LocalDate.now());

    }
}
