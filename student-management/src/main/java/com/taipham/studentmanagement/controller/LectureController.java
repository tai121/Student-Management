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

import com.taipham.studentmanagement.dto.request.LectureRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.LectureResponse;
import com.taipham.studentmanagement.service.LectureService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LectureController {
    LectureService lectureService;
    
    @PostMapping
    public ApiResponse<LectureResponse> create(@RequestBody @Valid LectureRequest request) {
        
        return ApiResponse.<LectureResponse>builder()
                            .result(lectureService.create(request))
                            .build();
    }

    @GetMapping
    public ApiResponse<List<LectureResponse>> getAll() {
        return ApiResponse.<List<LectureResponse>>builder()
        .result(lectureService.getAll())
        .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LectureResponse> getById(@PathVariable String id) {
        return ApiResponse.<LectureResponse>builder()
        .result(lectureService.getById(id))
        .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<LectureResponse> putMethodName(@PathVariable String id, @RequestBody LectureRequest request) {
        return ApiResponse.<LectureResponse>builder()
        .result(lectureService.update(id, request))
        .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        lectureService.delete(id);
        return ApiResponse.<String>builder()
        .result("Deleted")
        .build();
    }
}
