package com.klaudi73.blog.configuration;

import com.klaudi73.blog.services.MyAppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAppUserDetailsService myAppUserDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                //.antMatchers().permitAll()
                //.anyRequest().authenticated()
                .antMatchers("/").hasAnyRole("ADMIN", "USER", "ANONYMOUS")
                .and()
                    //formlogin
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .usernameParameter("login")
                    .passwordParameter("password")

                .and()
                    //logmeout
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")

                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error-view");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(myAppUserDetailsService).passwordEncoder(passwordEncoder);
    }

}
