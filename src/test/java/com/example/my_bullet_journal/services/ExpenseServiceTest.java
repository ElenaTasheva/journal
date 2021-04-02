package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.Expense;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.ExpenseEnum;
import com.example.my_bullet_journal.models.view.ExpenseViewModel;
import com.example.my_bullet_journal.repositories.ExpenseRepository;
import com.example.my_bullet_journal.services.impl.ExpenseServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpenseServiceTest {


    private ExpenseRepository mockedExpenseRepository;
    private UserService mockedUserService;
    private ModelMapper modelMapper;


    @Before
    public void init(){
        this.mockedExpenseRepository = Mockito.mock(ExpenseRepository.class);
        this.mockedUserService = Mockito.mock(UserService.class);
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void getCategoriesReturnsAListWithAllIncomeCategoryEnums() {
        ExpenseService expenseService = new ExpenseServiceImpl(mockedExpenseRepository,modelMapper, mockedUserService);
        List<ExpenseEnum> expenseEnums = expenseService.getCategories();
        Assert.assertEquals(13, expenseEnums.size());
    }

    @Test
    public void getAllIncomeOrderedByCategoryReturnsAListOfIncomeViewModels () {
        Mockito.when(mockedUserService.findByEmail("test@mail.bg"))
                .thenReturn((User) new User().setId((long) 1));
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add((Expense) new Expense().setCategory(ExpenseEnum.BILLS).setAmount(BigDecimal.valueOf(20)));
        expenseList.add((Expense) new Expense().setCategory(ExpenseEnum.BILLS).setAmount(BigDecimal.valueOf(10)));
        Mockito.when(mockedExpenseRepository.findAllAndOrderByCategory((long) 1)).thenReturn(expenseList);
        ExpenseService expenseService = new ExpenseServiceImpl(mockedExpenseRepository,modelMapper, mockedUserService);
        List<ExpenseViewModel> expenses = expenseService.getAllExpensesOrderByCategory("test@mail.bg");
        Assert.assertEquals(2, expenses.size());
        Assert.assertEquals(expenses.get(0).getCategory(), ExpenseEnum.BILLS);
        Assert.assertEquals(expenses.get(1).getCategory(), ExpenseEnum.BILLS);


    }

    @Test
    public void getTotalAmountOfExpensesReturnsTheSumOfAllExpensesIfThereIsAny() {
        Mockito.when(mockedUserService
                .findByEmail("test@email.bg")).thenReturn((User) new User().setId((long) 1));
        Mockito.when(mockedExpenseRepository.getTotal((long) 1)).thenReturn(BigDecimal.valueOf(100));
        ExpenseService expenseService = new ExpenseServiceImpl(mockedExpenseRepository,modelMapper, mockedUserService);

        Assert.assertEquals(BigDecimal.valueOf(100), expenseService.getTotalAmountOfExpenses("test@email.bg"));
    }
    @Test
    public void getTotalAmountOfExpensesZeroWhenThereIsNoIncomeRegistered() {
        Mockito.when(mockedUserService
                .findByEmail("test@email.bg")).thenReturn((User) new User().setId((long) 1));
        Mockito.when(mockedExpenseRepository.getTotal((long) 1)).thenReturn(null);
        ExpenseService expenseService = new ExpenseServiceImpl(mockedExpenseRepository,modelMapper, mockedUserService);

        Assert.assertEquals(BigDecimal.valueOf(0), expenseService.getTotalAmountOfExpenses("test@email.bg"));
    }
    @Test
    public void expenseSumByCategoryReturnsAMapWithTheCategoriesAndTheirsSum() {
        List<Object[]> result = new ArrayList<>();
        Object [] obj = new Object[2];
        obj[0] = ExpenseEnum.RENT;
        obj[1] = BigDecimal.valueOf(100);
        result.add(obj);
        Mockito.when(mockedExpenseRepository.finExpensesByCategories((long) 1))
                .thenReturn(result);
        ExpenseService expenseService = new ExpenseServiceImpl(mockedExpenseRepository,modelMapper, mockedUserService);
        HashMap<String,BigDecimal> expenseSum = expenseService.expenseSumByCategory((long) 1);
        Assert.assertTrue(expenseSum.containsKey("RENT"));
        Assert.assertEquals(BigDecimal.valueOf(100), expenseSum.get("RENT"));

    }



}
