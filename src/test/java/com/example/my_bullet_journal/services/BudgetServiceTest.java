package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.services.impl.BudgedServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;

public class BudgetServiceTest {

    private  ExpenseService mockedExpenseService;
    private  IncomeService mockedIncomeService;
    private  UserService mockedUserService;

    @Before
    public void init () {
        this.mockedExpenseService = Mockito.mock(ExpenseService.class);
        this.mockedIncomeService = Mockito.mock(IncomeService.class);
        this.mockedUserService = Mockito.mock(UserService.class);
    }

    @Test
    public void getExpensesListReturnsListWithParamNoDataWithValue100IfThereAreNoExpensesRegistered() {
        Mockito.when(mockedUserService.findByEmail("test@mail.bg")).thenReturn((User) new User().setId((long) 1));
        HashMap<String, BigDecimal> expenses = new HashMap<>();

        Mockito.when(mockedExpenseService.expenseSumByCategory((long) 1)).thenReturn(expenses);
        BudgetService budgetService = new BudgedServiceImpl(mockedExpenseService, mockedIncomeService, mockedUserService);
        budgetService.getExpensesList("test@mail.bg");
        BigDecimal bigDecimal = expenses.get("NO DATA");
        Assert.assertEquals(BigDecimal.valueOf(100),bigDecimal );
    }



    @Test
    public void getExpensesListReturnsListWithParamNoDataWithValue0IfThereAreAlreadyRegisteredExpenses() {
        Mockito.when(mockedUserService.findByEmail("test@mail.bg")).thenReturn((User) new User().setId((long) 1));
        HashMap<String, BigDecimal> expenses = new HashMap<>();
        expenses.put("Other", BigDecimal.valueOf(70));

        Mockito.when(mockedExpenseService.expenseSumByCategory((long) 1)).thenReturn(expenses);
        BudgetService budgetService = new BudgedServiceImpl(mockedExpenseService, mockedIncomeService, mockedUserService);
        budgetService.getExpensesList("test@mail.bg");
        BigDecimal bigDecimal = expenses.get("NO DATA");
        Assert.assertEquals(BigDecimal.valueOf(0),bigDecimal );
        Assert.assertEquals(BigDecimal.valueOf(70), expenses.get("Other"));
    }

    @Test
    public void getExpensesSumReturns0IfTheSumOfTheExpensesIsNull() {
        BudgetService budgetService = new BudgedServiceImpl(mockedExpenseService, mockedIncomeService, mockedUserService);
        Assert.assertEquals(BigDecimal.valueOf(0),budgetService.getExpensesSum());
    }

    @Test
    public void getExpensesSumReturnsTheValueOfExpensesWhenThereAreAny() {
        Mockito.when(mockedUserService.findByEmail("test@mail.bg")).thenReturn((User) new User().setId((long) 1));
        HashMap<String, BigDecimal> expenses = new HashMap<>();

        Mockito.when(mockedExpenseService.expenseSumByCategory((long) 1)).thenReturn(expenses);
        Mockito.when(mockedExpenseService.getTotalAmountOfExpenses("test@mail.bg")).thenReturn(BigDecimal.valueOf(100));
        BudgetService budgetService = new BudgedServiceImpl(mockedExpenseService, mockedIncomeService, mockedUserService);
        budgetService.getExpensesList("test@mail.bg");
        Assert.assertEquals(BigDecimal.valueOf(100),budgetService.getExpensesSum());
    }

    @Test
    public void getExpensesSumReturnsZeroWhenTheValueOfAllExpensesIsZero(){
        Mockito.when(mockedUserService.findByEmail("test@mail.bg")).thenReturn((User) new User().setId((long) 1));
        HashMap<String, BigDecimal> expenses = new HashMap<>();

        Mockito.when(mockedExpenseService.expenseSumByCategory((long) 1)).thenReturn(expenses);
        Mockito.when(mockedExpenseService.getTotalAmountOfExpenses("test@mail.bg")).thenReturn(null);
        BudgetService budgetService = new BudgedServiceImpl(mockedExpenseService, mockedIncomeService, mockedUserService);
        budgetService.getExpensesList("test@mail.bg");
        Assert.assertEquals(BigDecimal.valueOf(0),budgetService.getExpensesSum());
    }



    @Test
    public void getBalanceReturnsTheDifferenceBetweenExpensesAndIncome(){
        Mockito.when(mockedUserService.findByEmail("test@mail.bg")).thenReturn((User) new User().setId((long) 1));
        HashMap<String, BigDecimal> expenses = new HashMap<>();


        Mockito.when(mockedExpenseService.expenseSumByCategory((long) 1)).thenReturn(expenses);
        Mockito.when(mockedExpenseService.getTotalAmountOfExpenses("test@mail.bg")).thenReturn(BigDecimal.valueOf(70));
        Mockito.when(mockedIncomeService.getTotalIncome("test@mail.bg")).thenReturn(BigDecimal.valueOf(30));

        BudgetService budgetService = new BudgedServiceImpl(mockedExpenseService, mockedIncomeService, mockedUserService);
        budgetService.getExpensesList("test@mail.bg");
        Assert.assertEquals(BigDecimal.valueOf(-40),budgetService.getBalance());
    }
}
