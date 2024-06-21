package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.RegistrationRequest;
import com.taipham.studentmanagement.dto.response.RegistrationResponse;
import com.taipham.studentmanagement.entity.Course;
import com.taipham.studentmanagement.entity.Registration;
import com.taipham.studentmanagement.entity.Student;
import com.taipham.studentmanagement.entity.User;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.CourseRepository;
import com.taipham.studentmanagement.repository.InstructorRepository;
import com.taipham.studentmanagement.repository.LectureRepository;
import com.taipham.studentmanagement.repository.RegistrationRepository;
import com.taipham.studentmanagement.repository.StudentRepository;
import com.taipham.studentmanagement.repository.UserRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RegistrationService {
    CourseRepository courseRepository;

    LectureRepository lectureRepository;

    InstructorRepository instructorRepository;

    ModelMapper modelMapper;

    StudentRepository studentRepository;

    RegistrationRepository registrationRepository;

    UserRepository userRepository;

    public RegistrationResponse create(RegistrationRequest request,String username){
        Registration registration = new Registration();
        List<Course> courses = courseRepository.findAllById(request.getCourses());
        registration.setCourses(new HashSet<>(courses));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        Student student = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST)).getStudent();
        registration.setStudent(student);
        Double total = courses.stream().mapToDouble(Course::getPrice).sum();
        registration.setTotal(total);
        registration.setCreateAt(LocalDateTime.now());
        
        return modelMapper.map(registrationRepository.save(registration), RegistrationResponse.class);
    }

    public RegistrationResponse getPersionalRegistration(String username){
        Student student = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST)).getStudent();
        return modelMapper.map(registrationRepository.findTopByStudentIdOrderByCreateAtDesc(student.getId()), RegistrationResponse.class);
    }
    
}
