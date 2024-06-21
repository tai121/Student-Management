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

import com.taipham.studentmanagement.dto.request.LessionRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.LessionResponse;
import com.taipham.studentmanagement.service.LessionService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessionController {
    LessionService lessionService;
    
    @PostMapping
    public ApiResponse<LessionResponse> create(@RequestBody @Valid LessionRequest request) {
        
        return ApiResponse.<LessionResponse>builder()
                            .result(lessionService.create(request))
                            .build();
    }

    @GetMapping
    public ApiResponse<List<LessionResponse>> getAll() {
        return ApiResponse.<List<LessionResponse>>builder()
        .result(lessionService.getAll())
        .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LessionResponse> getById(@PathVariable String id) {
        return ApiResponse.<LessionResponse>builder()
        .result(lessionService.getById(id))
        .build();
    }
    
    @PutMapping("/{id}")
    public ApiResponse<LessionResponse> putMethodName(@PathVariable String id, @RequestBody LessionRequest request) {
        return ApiResponse.<LessionResponse>builder()
        .result(lessionService.update(id, request))
        .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable String id) {
        lessionService.delete(id);
        return ApiResponse.<String>builder()
        .result("Deleted")
        .build();
    }
}
