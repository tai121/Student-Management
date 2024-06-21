package com.taipham.studentmanagement.dto.response;



import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructorResponse {
    String id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;

    // Set<CourseResponse> courses;
}
