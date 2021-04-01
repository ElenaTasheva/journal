package com.example.my_bullet_journal.services.impl;

import com.example.my_bullet_journal.models.entities.Income;
import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.BudgetStatusEnum;
import com.example.my_bullet_journal.models.enums.IncomeEnum;
import com.example.my_bullet_journal.models.services.IncomeServiceModel;
import com.example.my_bullet_journal.models.view.IncomeViewModel;
import com.example.my_bullet_journal.repositories.IncomeRepository;
import com.example.my_bullet_journal.services.IncomeService;
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
public class IncomeServiceImpl implements IncomeService {


    private final IncomeRepository incomeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public IncomeServiceImpl(IncomeRepository incomeRepository, ModelMapper modelMapper, UserService userService) {
        this.incomeRepository = incomeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<IncomeEnum> getCategories() {
        return Arrays.stream(IncomeEnum.values()).collect(Collectors.toList());

    }

    @Override
    public void save(IncomeServiceModel incomeServiceModel, String email) {
        User user = this.userService.findByEmail(email);
        Income income = this.modelMapper.map(incomeServiceModel, Income.class);
        income.setUser(user);
        this.incomeRepository.save(income);
    }

    @Override
    public List<IncomeViewModel> getAllIncomesOrderByCategory (String email) {
        Long userId = this.userService.findByEmail(email).getId();
        return this.incomeRepository.findAllAndOrderByCategory(userId)
                .stream().map(income -> this.modelMapper.map(income,IncomeViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public BigDecimal getTotalIncome(String email) {
        Long userId = this.userService.findByEmail(email).getId();
        return this.incomeRepository.getTotal(userId) != null ? this.incomeRepository.getTotal(userId) : BigDecimal.valueOf(0) ;
    }

    @Override
    public HashMap<String, BigDecimal> incomeSumByCategory(Long userId) {
        HashMap<String, BigDecimal> result = new HashMap<>();
            List<Object[]> list = this.incomeRepository.sumIncomeByCategory(userId);
            for (Object[] ob: list) {
                String key = (String)ob[0];
                BigDecimal value = (BigDecimal) ob[1];
                result.put(key, value);

            }
            return result;
        }


        // completing the month and starting next month from 0
        @Scheduled(cron = "0 0 0 1 * *")
        protected void changeIncomeStatusToCompleted() {
            incomeRepository.changeMonthlyStatus(LocalDate.now())
                    .forEach(income -> {
                        income.setStatus(BudgetStatusEnum.COMPLETED);
                        incomeRepository.save(income);
                    });

        }


}

