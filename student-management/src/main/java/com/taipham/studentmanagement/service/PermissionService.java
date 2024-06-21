package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.PermissionRequest;
import com.taipham.studentmanagement.dto.response.PermissionResponse;
import com.taipham.studentmanagement.entity.Permission;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.PermissionRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;

    ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    public PermissionResponse create(PermissionRequest request){
        Permission permission = modelMapper.map(request, Permission.class);
        log.info("Created successfully.");
        return modelMapper.map(permissionRepository.save(permission), PermissionResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public PermissionResponse getById(String id){
        Permission permission = permissionRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(permission, PermissionResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public List<PermissionResponse> getAll(){
        List<Permission> permissions = permissionRepository.findAll();
        log.info("Retrieved successfully.");
        return permissions.stream().map(permission -> modelMapper.map(permission, PermissionResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
    public PermissionResponse update(String id, PermissionRequest request){
        Permission permission = permissionRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, permission);
        log.info("Update successfully.");
        return modelMapper.map(permissionRepository.save(permission), PermissionResponse.class);
    }

    @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
    public void delete(String id){
        permissionRepository.deleteById(id);
        log.info("Delete successfully.");
    }
}
