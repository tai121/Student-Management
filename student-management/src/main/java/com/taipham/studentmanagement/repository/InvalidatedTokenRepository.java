package com.taipham.studentmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taipham.studentmanagement.entity.InvalidatedToken;

@Repository
public interface InvalidatedTokenRepository extends CrudRepository<InvalidatedToken,String>{
    
}
