package com.taipham.studentmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taipham.studentmanagement.dto.request.StudentCreationRequest;
import com.taipham.studentmanagement.dto.request.StudentUpdateRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.StudentResponse;
import com.taipham.studentmanagement.service.StudentService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {
    StudentService studentService;
    
    @PostMapping
    public ApiResponse<StudentResponse> create(@RequestBody @Valid StudentCreationRequest request) {
        
        return ApiResponse.<StudentResponse>builder()
                            .result(studentService.create(request))
                            .build();
    }

    @GetMapping
    public ApiResponse<List<StudentResponse>> getAll() {
        return ApiResponse.<List<StudentResponse>>builder()
        .result(studentService.getAll())
        .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getById(@PathVariable String id) {
        return ApiResponse.<StudentResponse>builder()
        .result(studentService.getById(id))
        .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> putMethodName(@PathVariable String id, @RequestBody StudentUpdateRequest request) {
        return ApiResponse.<StudentResponse>builder()
        .result(studentService.update(id, request))
        .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        studentService.delete(id);
        return ApiResponse.<String>builder()
        .result("Deleted")
        .build();
    }
}
