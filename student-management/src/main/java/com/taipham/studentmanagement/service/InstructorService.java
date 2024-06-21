package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.InstructorRequest;
import com.taipham.studentmanagement.dto.response.InstructorResponse;
import com.taipham.studentmanagement.entity.Instructor;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.InstructorRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class InstructorService {
    InstructorRepository instructorRepository;

    ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('CREATE_INSTRUCTOR')")
    public InstructorResponse create(InstructorRequest request){
        Instructor instructor = modelMapper.map(request, Instructor.class);
        log.info("Created successfully.");
        return modelMapper.map(instructorRepository.save(instructor), InstructorResponse.class);
    }

    
    
    @PreAuthorize("hasAuthority('READ_INSTRUCTOR')")
    public InstructorResponse getById(String id){
        Instructor instructor = instructorRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(instructor, InstructorResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_INSTRUCTOR')")
    public List<InstructorResponse> getAll(){
        List<Instructor> instructors = instructorRepository.findAll();
        log.info("Retrieved successfully.");
        return instructors.stream().map(instructor -> modelMapper.map(instructor, InstructorResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_INSTRUCTOR')")
    public InstructorResponse update(String id, InstructorRequest request){
        Instructor instructor = instructorRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, instructor);
        log.info("Update successfully.");
        return modelMapper.map(instructorRepository.save(instructor), InstructorResponse.class);
    }

    
    @PreAuthorize("hasAuthority('DELETE_INSTRUCTOR')")
    public void delete(String id){
        instructorRepository.deleteById(id);
        log.info("Delete successfully.");
    }
}
