package com.taipham.studentmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taipham.studentmanagement.dto.request.RegistrationRequest;
import com.taipham.studentmanagement.dto.response.ApiResponse;
import com.taipham.studentmanagement.dto.response.RegistrationResponse;
import com.taipham.studentmanagement.service.RegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {
    RegistrationService registrationService;

    @PostMapping
    public ApiResponse<RegistrationResponse> postMethodName(@RequestBody RegistrationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<RegistrationResponse>builder()
                            .result(registrationService.create(request, username))
                            .build();
    }

    @GetMapping("/me")
    public ApiResponse<RegistrationResponse> getMethodName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ApiResponse.<RegistrationResponse>builder()
                            .result(registrationService.getPersionalRegistration(username))
                            .build();
    }
    
    
}
