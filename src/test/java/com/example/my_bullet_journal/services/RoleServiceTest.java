package com.example.my_bullet_journal.services;

import com.example.my_bullet_journal.repositories.RoleRepository;
import com.example.my_bullet_journal.services.impl.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RoleServiceTest {

    RoleRepository mockedRoleRepository;

    @Before
    public void init() {
        mockedRoleRepository = Mockito.mock(RoleRepository.class);

    }

    @Test
    public void rolesAreSeededSuccessfullyIfRepoIsEmpty(){
        String expected = "Roles seeded successfully";
        Mockito.when(mockedRoleRepository.count()).thenReturn((long) 0);
        RoleService roleService = new RoleServiceImpl(mockedRoleRepository);
        String result = roleService.seedRoles();
        Assert.assertEquals(expected, result);
    }


}
