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

import com.taipham.studentmanagement.dto.request.InstructorRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.InstructorResponse;
import com.taipham.studentmanagement.service.InstructorService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instructors")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InstructorController {
    InstructorService instructorService;
    
    @PostMapping
    public ApiResponse<InstructorResponse> create(@RequestBody @Valid InstructorRequest request) {
        
        return ApiResponse.<InstructorResponse>builder()
                            .result(instructorService.create(request))
                            .build();
    }

    @GetMapping
    public ApiResponse<List<InstructorResponse>> getAll() {
        return ApiResponse.<List<InstructorResponse>>builder()
        .result(instructorService.getAll())
        .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<InstructorResponse> getById(@PathVariable String id) {
        return ApiResponse.<InstructorResponse>builder()
        .result(instructorService.getById(id))
        .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<InstructorResponse> putMethodName(@PathVariable String id, @RequestBody InstructorRequest request) {
        return ApiResponse.<InstructorResponse>builder()
        .result(instructorService.update(id, request))
        .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        instructorService.delete(id);
        return ApiResponse.<String>builder()
        .result("Deleted")
        .build();
    }
}
