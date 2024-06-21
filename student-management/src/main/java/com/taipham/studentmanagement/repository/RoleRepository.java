package com.taipham.studentmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taipham.studentmanagement.entity.Role;

public interface RoleRepository extends JpaRepository<Role,String>{
    List<Role> findByNameIn(List<String> names);

    Role findByName(String name);
}
