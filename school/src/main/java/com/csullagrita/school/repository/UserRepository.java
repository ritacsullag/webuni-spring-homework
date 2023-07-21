package com.csullagrita.school.repository;

import com.csullagrita.school.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<LoginUser, Integer> {

    Optional<LoginUser> findByUsername(String username);

}
