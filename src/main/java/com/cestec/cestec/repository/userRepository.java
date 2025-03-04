package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.cestec.cestec.model.securityLogin.sp_user;

public interface userRepository extends JpaRepository<sp_user, Integer>{
    UserDetails findByLogin(String login);
}
