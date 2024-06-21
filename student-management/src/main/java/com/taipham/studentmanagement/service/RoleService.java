package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.RoleRequest;
import com.taipham.studentmanagement.dto.response.RoleResponse;
import com.taipham.studentmanagement.entity.Permission;
import com.taipham.studentmanagement.entity.Role;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.PermissionRepository;
import com.taipham.studentmanagement.repository.RoleRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;

    PermissionRepository permissionRepository;

    ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public RoleResponse create(RoleRequest request){
        Role role = modelMapper.map(request, Role.class);
        Set<Permission> permissions = new HashSet<>(permissionRepository.findByNameIn(request.getPermissions()));
        role.setPermissions(permissions);
        log.info("Created successfully.");
        return modelMapper.map(roleRepository.save(role), RoleResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_ROLE')")
    public RoleResponse getById(String id){
        Role role = roleRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(role, RoleResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_ROLE')")
    public List<RoleResponse> getAll(){

        log.info("Retrieved successfully.");
        return roleRepository.findAll().stream().map(role -> modelMapper.map(role, RoleResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public RoleResponse update(String id, RoleRequest request){
        Role role = roleRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, role);
        if(request.getPermissions()!=null && request.getPermissions().size()!=0)
            role.setPermissions(new HashSet<>(permissionRepository.findByNameIn(request.getPermissions())));
        log.info("Update successfully.");
        return modelMapper.map(roleRepository.save(role), RoleResponse.class);
    }

    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public void delete(String id){
        roleRepository.deleteById(id);
        log.info("Deleted successfully.");
    }
}
