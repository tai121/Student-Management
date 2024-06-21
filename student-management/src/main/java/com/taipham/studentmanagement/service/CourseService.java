package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.CourseRequest;
import com.taipham.studentmanagement.dto.response.CourseResponse;
import com.taipham.studentmanagement.entity.Course;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.CourseRepository;
import com.taipham.studentmanagement.repository.InstructorRepository;
import com.taipham.studentmanagement.repository.LectureRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CourseService {
    CourseRepository courseRepository;

    LectureRepository lectureRepository;

    InstructorRepository instructorRepository;

    ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    public CourseResponse create(CourseRequest request){
        Course course = modelMapper.map(request, Course.class);
        course.setLecture(lectureRepository.findById(request.getLectureId()).orElseThrow(()-> new AppException(ErrorCode.ID_DOES_NOT_EXIST)));
        course.setInstructor(instructorRepository.findById(request.getInstructorId()).orElseThrow(()-> new AppException(ErrorCode.ID_DOES_NOT_EXIST)));
        log.info("Created successfully.");
        return modelMapper.map(courseRepository.saveAndFlush(course), CourseResponse.class);
    }

    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CourseResponse getById(String id){
        Course course = courseRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(course, CourseResponse.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CourseResponse> getAll(){
        List<Course> courses = courseRepository.findAll();
        log.info("Retrieved successfully.");
        return courses.stream().map(course -> modelMapper.map(course, CourseResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE')")
    public CourseResponse update(String id, CourseRequest request){
        Course course = courseRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, course);
        log.info("Update successfully.");
        return modelMapper.map(courseRepository.save(course), CourseResponse.class);
    }

    @PreAuthorize("hasAuthority('DELETE_COURSE')")
    public void delete(String id){
        courseRepository.deleteById(id);
        log.info("Delete successfully.");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public List<CourseResponse> getAllAvalableCourse(){
        List<Course> courses = courseRepository.findByRegistrationEndAtAfter(LocalDate.now());
        return courses.stream().map(course -> modelMapper.map(course, CourseResponse.class)).toList();
    }
}
