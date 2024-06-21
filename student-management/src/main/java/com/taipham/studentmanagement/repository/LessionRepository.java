package com.taipham.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Lession;

public interface LessionRepository extends JpaRepository<Lession,String>{
    
}
