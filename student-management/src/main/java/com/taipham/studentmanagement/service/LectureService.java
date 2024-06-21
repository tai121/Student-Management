package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.LectureRequest;
import com.taipham.studentmanagement.dto.response.LectureResponse;
import com.taipham.studentmanagement.entity.Lecture;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.LectureRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class LectureService {
    LectureRepository lectureRepository;

    ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('CREATE_LECTURE')")
    public LectureResponse create(LectureRequest request){
        Lecture lecture = modelMapper.map(request, Lecture.class);
        log.info("Created successfully.");
        return modelMapper.map(lectureRepository.save(lecture), LectureResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_LECTURE')")
    public LectureResponse getById(String id){
        Lecture lecture = lectureRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(lecture, LectureResponse.class);
    }

    
    @PreAuthorize("hasAuthority('READ_LECTURE')")
    public List<LectureResponse> getAll(){
        List<Lecture> lectures = lectureRepository.findAll();
        log.info("Retrieved successfully.");
        // log.info(String.valueOf(lectures[0));
        return lectures.stream().map(lecture -> modelMapper.map(lecture, LectureResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_LECTURE')")
    public LectureResponse update(String id, LectureRequest request){
        Lecture lecture = lectureRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, lecture);
        log.info("Update successfully.");
        return modelMapper.map(lectureRepository.save(lecture), LectureResponse.class);
    }

    
    
    @PreAuthorize("hasAuthority('DELETE_LECTURE')")
    public void delete(String id){
        lectureRepository.deleteById(id);
        log.info("Delete successfully.");
    }
}
