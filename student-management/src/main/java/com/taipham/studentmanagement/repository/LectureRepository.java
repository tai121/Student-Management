package com.taipham.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture,String>{
    
}
