package com.baidu.springsecurity.security.config;

import com.baidu.springsecurity.security.filter.LoginFilter;
import com.baidu.springsecurity.security.handle.MyAuthenticationFailureHandler;
import com.baidu.springsecurity.security.handle.MyAuthenticationSuccessHandler;
import com.baidu.springsecurity.security.handle.MyLogoutSuccessHandler;
import com.baidu.springsecurity.security.user.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public SpringSecurity(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager){
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setPasswordParameter("password");
        loginFilter.setUsernameParameter("username");
        loginFilter.setFilterProcessesUrl("/login");
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        return loginFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            http
                .csrf().disable()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/login.html").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login.html")
                .and().logout().logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().userDetailsService(userDetailService);
         http.addFilterAt(loginFilter(http.getSharedObject(AuthenticationManager.class)), UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
