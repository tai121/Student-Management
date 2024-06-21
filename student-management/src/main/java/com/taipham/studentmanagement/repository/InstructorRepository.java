package com.taipham.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor,String>{
    
}
