package com.example.my_bullet_journal.config;

import com.example.my_bullet_journal.models.entities.User;
import com.example.my_bullet_journal.models.enums.RoleEnum;
import com.example.my_bullet_journal.repositories.RoleRepository;
import com.example.my_bullet_journal.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp{


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public FacebookConnectionSignup(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public String execute(Connection<?> connection) {
        if(userRepository.findByEmail(connection.getDisplayName()).isEmpty()) {
            User user = new User();
            user.setUsername(connection.getDisplayName());
            user.setPassword("facebook");
            user.setEmail(connection.getDisplayName());
            user.setRoles(Set.of(roleRepository.findByRoleEnum(RoleEnum.USER).get()));
            userRepository.save(user);
        }

        return connection.getDisplayName();
    }


    }
