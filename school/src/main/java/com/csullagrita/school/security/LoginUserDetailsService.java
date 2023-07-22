package com.csullagrita.school.security;


import com.csullagrita.school.model.LoginUser;
import com.csullagrita.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));

        return new User(username, loginUser.getPassword(),
                List.of(new SimpleGrantedAuthority(loginUser.getUserType().toString()))
        );
    }

}
