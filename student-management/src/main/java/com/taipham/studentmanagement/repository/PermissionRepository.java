package com.taipham.studentmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission,String>{
    List<Permission> findByNameIn(List<String> names);
}
