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

import com.taipham.studentmanagement.dto.request.CourseRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.CourseResponse;
import com.taipham.studentmanagement.service.CourseService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CourseController {
    CourseService courseService;
    
    @PostMapping
    public ApiResponse<CourseResponse> create(@RequestBody @Valid CourseRequest request) {
        
        return ApiResponse.<CourseResponse>builder()
                            .result(courseService.create(request))
                            .build();
    }

    @GetMapping
    public ApiResponse<List<CourseResponse>> getAll() {
        return ApiResponse.<List<CourseResponse>>builder()
        .result(courseService.getAll())
        .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseResponse> getById(@PathVariable String id) {
        return ApiResponse.<CourseResponse>builder()
        .result(courseService.getById(id))
        .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<CourseResponse> putMethodName(@PathVariable String id, @RequestBody CourseRequest request) {
        return ApiResponse.<CourseResponse>builder()
        .result(courseService.update(id, request))
        .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        courseService.delete(id);
        return ApiResponse.<String>builder()
        .result("Deleted")
        .build();
    }

    @GetMapping("/avalable")
    public ApiResponse<List<CourseResponse>> getMethodName() {
        log.info("there");
        return ApiResponse.<List<CourseResponse>>builder()
        .result(courseService.getAllAvalableCourse())
        .build();
    }
    
}
