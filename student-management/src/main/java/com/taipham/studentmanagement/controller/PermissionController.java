package com.taipham.studentmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taipham.studentmanagement.dto.request.PermissionRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.PermissionResponse;
import com.taipham.studentmanagement.service.PermissionService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;
    
    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody @Valid PermissionRequest request) {
        
        return ApiResponse.<PermissionResponse>builder()
                            .result(permissionService.create(request))
                            .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
        .result(permissionService.getAll())
        .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PermissionResponse> getById(@PathVariable String id) {
        return ApiResponse.<PermissionResponse>builder()
        .result(permissionService.getById(id))
        .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<PermissionResponse> putMethodName(@PathVariable String id, @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
        .result(permissionService.update(id, request))
        .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        permissionService.delete(id);
        return ApiResponse.<String>builder()
        .result("Deleted")
        .build();
    }
}
