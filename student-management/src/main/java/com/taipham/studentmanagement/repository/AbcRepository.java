package com.taipham.studentmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taipham.studentmanagement.entity.Abc;

@Repository
public interface AbcRepository extends CrudRepository<Abc, String>{
    
}
