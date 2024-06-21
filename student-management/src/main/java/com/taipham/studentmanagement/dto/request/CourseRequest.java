package com.taipham.studentmanagement.dto.request;

import java.time.LocalDate;

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
public class CourseRequest {
    LocalDate registrationStartAt;
    LocalDate registrationEndAt;
    LocalDate courseStartAt;
    LocalDate courseEndAt;
    Double price;

    String lectureId;

    String instructorId;
}
