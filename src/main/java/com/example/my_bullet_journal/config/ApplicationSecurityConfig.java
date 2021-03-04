package com.example.my_bullet_journal.config;


import com.example.my_bullet_journal.services.impl.JournalDbUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JournalDbUserService journalDbUserService;
    private final PasswordEncoder passwordEncoder;


    public ApplicationSecurityConfig(JournalDbUserService journalDbUserService, PasswordEncoder passwordEncoder) {
        this.journalDbUserService = journalDbUserService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.
                 authorizeRequests().
                 antMatchers("/colorlib-regform-7/**", "/files/**", "/sticky-notes/**", "/templated-epilogue/**").permitAll()
                 .antMatchers("/", "/users/login", "/users/register", "/expenses/add").permitAll()
                 .antMatchers("/**").authenticated()
                 .and().formLogin().loginPage("/users/login")
                 .usernameParameter("username")
                 .passwordParameter("password")
                 .defaultSuccessUrl("/")
                 .failureForwardUrl("/users/login");
//                 .and()
//                 .rememberMe()
//                 .rememberMeParameter("remember-me")
//                 .key("remember-me Me Encryption Key")
//                 .rememberMeCookieName("rememberMeCookieName")
//                 .tokenValiditySeconds(10000);

        http.csrf().disable();

         //todo change the failureLink
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(journalDbUserService).
                passwordEncoder(passwordEncoder);
    }
}

