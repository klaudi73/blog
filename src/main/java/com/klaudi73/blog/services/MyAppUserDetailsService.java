package com.klaudi73.blog.services;

import com.klaudi73.blog.models.UserEntity;
import com.klaudi73.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MyAppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity user = userRepo.getByName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String name = user.getName();
        //String password = passwordEncoder.encode(user.getPassword());
        String password = user.getPassword();

        UserDetails userDetails = new User(name, password, Arrays.asList(authority));

        return userDetails;
    }

    public UserDetails loadUserByLogin(String login) {

        UserEntity user = userRepo.getByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        UserDetails userDetails = new User(userLogin, userPassword, Arrays.asList(authority));

        return userDetails;
    }
}
