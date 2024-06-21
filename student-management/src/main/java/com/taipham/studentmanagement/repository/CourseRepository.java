package com.taipham.studentmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Course;

public interface CourseRepository extends JpaRepository<Course,String>{
    List<Course> findByRegistrationEndAtAfter(LocalDate now);
}
