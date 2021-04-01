package com.example.my_bullet_journal.config;


import com.example.my_bullet_journal.repositories.UserRepository;
import com.example.my_bullet_journal.services.impl.JournalDbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JournalDbUserService journalDbUserService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public ApplicationSecurityConfig(JournalDbUserService journalDbUserService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.journalDbUserService = journalDbUserService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Autowired
    private FacebookConnectionSignup facebookConnectionSignup;

    @Value("${social.facebook.appSecret}")
    String appSecret;

    @Value("${social.facebook.appId}")
    String appId;



    @Bean
    public ProviderSignInController providerSignInController() {
        ConnectionFactoryLocator connectionFactoryLocator =
                connectionFactoryLocator();
        UsersConnectionRepository usersConnectionRepository =
                getUsersConnectionRepository(connectionFactoryLocator);
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp(facebookConnectionSignup);
        return new ProviderSignInController(connectionFactoryLocator,
                usersConnectionRepository, new FacebookSignInAdapter());
    }

    private ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
        return registry;
    }

    private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator
                                                                           connectionFactoryLocator) {
        return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.
                 authorizeRequests().
                 antMatchers("/colorlib-regform-7/**", "/files/**", "/sticky-notes/**", "/templated-epilogue/**").permitAll()
                 .antMatchers("/", "/users/login", "/users/register", "/signin/facebook").permitAll()
                 .antMatchers("/**").authenticated()
                 .and().formLogin().loginPage("/users/login")
                 .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                 .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                 .defaultSuccessUrl("/home")
                 .failureForwardUrl("/users/login-error")
                 .and()
                 .logout()
                 .logoutUrl("/logout").
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true).
                        deleteCookies("JSESSIONID");
//                 .and()
//                 .rememberMe()
//                 .rememberMeParameter("remember-me")
//                 .key("remember-me Me Encryption Key")
//                 .rememberMeCookieName("rememberMeCookieName")
//                 .tokenValiditySeconds(10000);

        // http.csrf().disable();

    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(journalDbUserService).
                passwordEncoder(passwordEncoder);
    }
}

