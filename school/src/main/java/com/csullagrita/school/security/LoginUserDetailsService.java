package com.csullagrita.school.security;


import com.csullagrita.school.model.Course;
import com.csullagrita.school.model.LoginUser;
import com.csullagrita.school.model.Student;
import com.csullagrita.school.model.Teacher;
import com.csullagrita.school.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Transactional //lazy init exceptionok elkerulese miatt (lecsatolt entityk)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return createFacebookUserDetails(loginUser);
        //JwtService-ben is kell parse-olni
    }

    public static UserDetails createFacebookUserDetails(LoginUser loginUser) {
        Set<Course> courses = null;
        if(loginUser instanceof Teacher) {
            courses = ((Teacher)loginUser).getCourses();
        } else if (loginUser instanceof Student) {
            courses = ((Student)loginUser).getCourses();
        }

        return new UserInfo(loginUser.getUsername(), loginUser.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(loginUser.getUserType().toString())),
                courses == null ? Collections.emptyList() :
                        courses.stream().map(course -> (int) course.getId()).toList());
    }

}
