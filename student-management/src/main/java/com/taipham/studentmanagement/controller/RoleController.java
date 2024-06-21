package com.taipham.studentmanagement.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taipham.studentmanagement.dto.request.RoleRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.RoleResponse;
import com.taipham.studentmanagement.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;
    
    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody @Valid RoleRequest request) {
        
        return ApiResponse.<RoleResponse>builder()
                            .result(roleService.create(request))
                            .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // authentication.getAuthorities().forEach(grandAuthority-> log.info(grandAuthority.getAuthority()));
        
        log.info(authentication.getName());
        return ApiResponse.<List<RoleResponse>>builder()
        .result(roleService.getAll())
        .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> getById(@PathVariable String id) {
        return ApiResponse.<RoleResponse>builder()
        .result(roleService.getById(id))
        .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<RoleResponse> putMethodName(@PathVariable String id, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
        .result(roleService.update(id, request))
        .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        roleService.delete(id);
        return ApiResponse.<String>builder()
        .result("Deleted")
        .build();
    }
}
