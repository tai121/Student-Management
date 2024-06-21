package com.taipham.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.User;

public interface UserRepository extends JpaRepository<User,String>{

    Optional<User> findByUsername(String username);
    
}
