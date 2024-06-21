package com.taipham.studentmanagement.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreationRequest {
    String studentCode;
    String firstName;
    String lastName;
    @Email
    String email;
    String phoneNumber;
    LocalDate dateOfBirth;
    String className;
}
