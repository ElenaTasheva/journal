package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.models.entities.Income;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.IncomeEnum;
import com.example.my_bullet_journal.models.view.IncomeViewModel;
import com.example.my_bullet_journal.repositories.IncomeRepository;
import com.example.my_bullet_journal.services.impl.IncomeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IncomeServiceTest {

    private IncomeRepository mockedIncomeRepository;
    private  UserService mockedUserService;
    private ModelMapper modelMapper;


    @Before
    public void init(){
        this.mockedIncomeRepository = Mockito.mock(IncomeRepository.class);
        this.mockedUserService = Mockito.mock(UserService.class);
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void getCategoriesReturnsAListWithAllIncomeCategoryEnums() {
        IncomeService incomeService = new IncomeServiceImpl(mockedIncomeRepository,modelMapper, mockedUserService);
        List<IncomeEnum> incomes = incomeService.getCategories();
        Assert.assertEquals(4, incomes.size());
    }

    @Test
    public void getAllIncomeOrderedByCategoryReturnsAListOfIncomeViewModels () {
        Mockito.when(mockedUserService.findByEmail("test@mail.bg"))
                .thenReturn((User) new User().setId((long) 1));
        List<Income> incomeList = new ArrayList<>();
        incomeList.add((Income) new Income().setCategory(IncomeEnum.DEPOSIT).setAmount(BigDecimal.valueOf(20)));
        incomeList.add((Income) new Income().setCategory(IncomeEnum.INVESTMENT).setAmount(BigDecimal.valueOf(10)));
        Mockito.when(mockedIncomeRepository.findAllAndOrderByCategory((long) 1)).thenReturn(incomeList);
        IncomeService incomeService = new IncomeServiceImpl(mockedIncomeRepository,modelMapper, mockedUserService);
        List<IncomeViewModel> income = incomeService.getAllIncomesOrderByCategory("test@mail.bg");
        Assert.assertEquals(2, income.size());
        Assert.assertEquals(income.get(0).getCategory(), IncomeEnum.DEPOSIT);
        Assert.assertEquals(income.get(1).getCategory(), IncomeEnum.INVESTMENT);


    }

    @Test
    public void getTotalIncomeReturnsTheSumOfAllIncomeIfThereIsAny() {
        Mockito.when(mockedUserService
                .findByEmail("test@email.bg")).thenReturn((User) new User().setId((long) 1));
        Mockito.when(mockedIncomeRepository.getTotal((long) 1)).thenReturn(BigDecimal.valueOf(100));
        IncomeService incomeService = new IncomeServiceImpl(mockedIncomeRepository,modelMapper, mockedUserService);

        Assert.assertEquals(BigDecimal.valueOf(100), incomeService.getTotalIncome("test@email.bg"));
    }
    @Test
    public void getTotalIncomeReturnsZeroWhenThereIsNoIncomeRegistered() {
        Mockito.when(mockedUserService
                .findByEmail("test@email.bg")).thenReturn((User) new User().setId((long) 1));
        Mockito.when(mockedIncomeRepository.getTotal((long) 1)).thenReturn(null);
        IncomeService incomeService = new IncomeServiceImpl(mockedIncomeRepository,modelMapper, mockedUserService);

        Assert.assertEquals(BigDecimal.valueOf(0), incomeService.getTotalIncome("test@email.bg"));
    }
    @Test
    public void incomeSumByCategoryReturnsAMapWithTheCategoriesAndTheirsSum() {
        List<Object[]> result = new ArrayList<>();
        Object [] obj = new Object[2];
        obj[0] = IncomeEnum.DEPOSIT;
        obj[1] = BigDecimal.valueOf(100);
        result.add(obj);
        Mockito.when(mockedIncomeRepository.sumIncomeByCategory((long) 1))
                .thenReturn(result);
        IncomeService incomeService = new IncomeServiceImpl(mockedIncomeRepository,modelMapper, mockedUserService);
        HashMap<String,BigDecimal> incomeSum = incomeService.incomeSumByCategory((long) 1);
        Assert.assertTrue(incomeSum.containsKey("DEPOSIT"));
        Assert.assertEquals(BigDecimal.valueOf(100), incomeSum.get("DEPOSIT"));

    }



}
