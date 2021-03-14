package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.Income;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.IncomeEnum;
import com.example.my_bullet_journal.models.services.IncomeServiceModel;
import com.example.my_bullet_journal.models.view.IncomeViewModel;
import com.example.my_bullet_journal.repositories.IncomeRepository;
import com.example.my_bullet_journal.services.IncomeService;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeServiceImpl implements IncomeService {


    private final IncomeRepository incomeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JournalDbUserService journalDbUserService;
    private User user;

    public IncomeServiceImpl(IncomeRepository incomeRepository, ModelMapper modelMapper, UserService userService, JournalDbUserService journalDbUserService) {
        this.incomeRepository = incomeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.journalDbUserService = journalDbUserService;
        setCurrentUser();
    }

    @Override
    public List<IncomeEnum> getCategories() {
        return Arrays.stream(IncomeEnum.values()).collect(Collectors.toList());

    }

    @Override
    public void save(IncomeServiceModel incomeServiceModel) {
        Income income = this.modelMapper.map(incomeServiceModel, Income.class);
        //todo: get the currently login user and delete userRepo from here and from Income;
        this.incomeRepository.save(income);
    }

    @Override
    public List<IncomeViewModel> getAllIncome() {
        return this.incomeRepository.findAllAndOrderByCategory()
                .stream().map(income -> this.modelMapper.map(income,IncomeViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalIncome() {
        return this.incomeRepository.getTotal() != null ? this.incomeRepository.getTotal() : BigDecimal.valueOf(0) ;
    }

    @Override
    public HashMap<String, BigDecimal> incomeByCategory() {
        HashMap<String, BigDecimal> result = new HashMap<>();
            List<Object[]> list = this.incomeRepository.findIncomeByCategory();
            for (Object[] ob: list) {
                String key = (String)ob[0];
                BigDecimal value = (BigDecimal) ob[1];
                result.put(key, value);

            }
            return result;
        }


        private void setCurrentUser(){
            user = userService.findByEmail(this.journalDbUserService.getCurrentUserEmail());
        }
    }

