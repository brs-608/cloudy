package com.burmic.cloudy.Configurations;


import com.burmic.cloudy.Filters.JwtFilter;
import com.burmic.cloudy.Services.UserService;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    DataSource dataSource;
    JwtFilter jwtFilter;
    public SecurityConfig(@Lazy JwtFilter jwtFilter){
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) ->{
            csrf.disable();
        });
        http.authorizeHttpRequests((authz) -> {
            authz.requestMatchers("/user").hasRole("User");
            authz.requestMatchers("/auth/login").permitAll();
//            authz.requestMatchers("/users/get-user/**").permitAll();
            authz.requestMatchers("/").permitAll();
            authz.requestMatchers("/chat","/chat/history/**").permitAll();

            authz.requestMatchers("/auth/create-user").permitAll();
            authz.requestMatchers("/admin/**").hasRole("Admin");
            authz.anyRequest().authenticated();
        });
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.authorizeHttpRequests((auth) -> );
//        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(jdbcUserDetailsManager());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(List.of(daoAuthenticationProvider));
    }
    @Bean
    public UserDetailsManager jdbcUserDetailsManager(){
    	JdbcUserDetailsManager userManger = new JdbcUserDetailsManager(dataSource);
        userManger.setUsersByUsernameQuery("SELECT email_id, password, enabled FROM users WHERE email_id = ?");
        userManger.setAuthoritiesByUsernameQuery("SELECT email_id,role FROM users WHERE email_id = ?");
        return userManger;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
