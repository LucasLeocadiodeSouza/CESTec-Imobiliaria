package com.cestec.cestec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cestec.cestec.model.sp_user;
import org.springframework.security.core.userdetails.UserDetails;

public interface userRepository extends JpaRepository<sp_user, Integer>{
    UserDetails findByLogin(String login);
}
