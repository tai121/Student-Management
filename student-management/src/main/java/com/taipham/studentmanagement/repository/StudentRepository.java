package com.taipham.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Student;

public interface StudentRepository extends JpaRepository<Student,String>{
    Student findByStudentCode(String studentCode);
} 
