package com.taipham.studentmanagement.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.taipham.studentmanagement.entity.Lession;

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
public class CourseResponse {
    String id;
    LocalDate registrationStartAt;
    LocalDate registrationEndAt;
    LocalDate courseStartAt;
    LocalDate courseEndAt;
    Double price;

    LectureResponse lecture;

    InstructorResponse instructor;

    Set<Lession> lessions;
}
