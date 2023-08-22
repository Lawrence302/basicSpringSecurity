package com.example.springsecuritycourse.config;

import com.example.springsecuritycourse.service.UserInfoService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // rather than using the userDetails here, i want to get my own from the db
    @Bean
    public UserDetailsService user() {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("passworda"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
        return new UserInfoService(); // this is the one that will be used to get the user from the db
        // instead of the in memory one above UserInfoService extends
        // UserDetailsService and implements loadUserByUsername
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //@Order(SecurityProperties.BASIC_AUTH_ORDER) // This is the default order for Basic Authentication
    // if we want this one to take precedence over the default one,
        // we can use the following: without the @Order annotation
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/greetings/hello").permitAll()
                                .requestMatchers("/greetings/add").permitAll()
                                .requestMatchers("/greetings/hi").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        System.out.println("SecurityConfig.securityFilterChain()");
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider   provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(user());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
