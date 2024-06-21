package com.taipham.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration,String>{
    Optional<Registration> findTopByStudentIdOrderByCreateAtDesc(String studentId);
}
