package com.security.spring_security.Repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.spring_security.Models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    
    User findByUserName(String name);
}
