package com.baidu.springsecurity.config;

import com.baidu.springsecurity.security.MyAuthenticationFailureHandler;
import com.baidu.springsecurity.security.MyAuthenticationSuccessHandler;
import com.baidu.springsecurity.security.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/login.html").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailureHandler())
                .and().logout().logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
