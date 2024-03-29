//package com.example.my_bullet_journal.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
//import org.springframework.social.connect.support.ConnectionFactoryRegistry;
//import org.springframework.social.connect.web.ProviderSignInController;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//
//public class FacebookSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private FacebookConnectionSignup facebookConnectionSignup;
//
//    @Value("${social.facebook.appSecret}")
//    String appSecret;
//
//    @Value("${spring.social.facebook.appId}")
//    String appId;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/login*", "/signin/**", "/signup/**").permitAll()
//                .antMatchers("/**").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").permitAll();
//
//    }
//
//    @Bean
//    public ProviderSignInController providerSignInController() {
//        ConnectionFactoryLocator connectionFactoryLocator =
//                connectionFactoryLocator();
//        UsersConnectionRepository usersConnectionRepository =
//                getUsersConnectionRepository(connectionFactoryLocator);
//        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
//                .setConnectionSignUp(facebookConnectionSignup);
//        return new ProviderSignInController(connectionFactoryLocator,
//                usersConnectionRepository, new FacebookSignInAdapter(journalDbUserService));
//    }
//
//    private ConnectionFactoryLocator connectionFactoryLocator() {
//        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
//        registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));
//        return registry;
//    }
//
//    private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator
//                                                                           connectionFactoryLocator) {
//        return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
//    }
//
//}
