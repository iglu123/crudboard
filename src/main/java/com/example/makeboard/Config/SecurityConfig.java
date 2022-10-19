package com.example.makeboard.Config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable();

//        http
//            .authorizeRequests()
//            .antMatchers("/home").permitAll()                 //해당 url에 대한 인증 정보 요구를 하지 않음
//            .antMatchers("/user").hasAnyRole("USER", "ADMIN") //해당 url에 대한 역할 정보 요구
//            .antMatchers("/admin").hasAnyRole("ADMIN")
//            .anyRequest().authenticated();                    //인증은 요구하지만 인가는 요가하지 않는다는 설정

                //인증- 해당 사용자가 본인이 맞는지 확인하는 절차
                //인가- 인증된 사용자가 요청한 자원에 접근 가능한지를 결정하는 절차

        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                    .and()
                        .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
        ;


        http
                .formLogin()
                .defaultSuccessUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password");

        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");

        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/expired");

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

}
