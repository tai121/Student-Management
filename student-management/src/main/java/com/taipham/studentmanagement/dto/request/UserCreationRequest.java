package com.taipham.studentmanagement.dto.request;

import java.util.List;

import com.taipham.studentmanagement.validator.PasswordConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min=5,max = 12,message = "USERNAME_SIZE_ERROR")
    String username;
    @Size(min=8, message = "PASSWORD_SIZE_ERROR")
    @PasswordConstraint(message = "PASSWORD_PATTERN_ERROR")
    String password;
    @Email
    String backupEmail;
    List<String> roles;
}
