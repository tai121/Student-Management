package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.LessionRequest;
import com.taipham.studentmanagement.dto.response.LessionResponse;
import com.taipham.studentmanagement.entity.Lession;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.LessionRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j

public class LessionService {
    LessionRepository lessionRepository;

    ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('CREATE_LESSION')")
    public LessionResponse create(LessionRequest request){
        Lession lession = modelMapper.map(request, Lession.class);
        log.info("Created successfully.");
        return modelMapper.map(lessionRepository.save(lession), LessionResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_LESSION')")
    public LessionResponse getById(String id){
        Lession lession = lessionRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(lession, LessionResponse.class);
    }

    
    @PreAuthorize("hasAuthority('READ_LESSION')")
    public List<LessionResponse> getAll(){
        List<Lession> lessions = lessionRepository.findAll();
        log.info("Retrieved successfully.");
        return lessions.stream().map(lession -> modelMapper.map(lession, LessionResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_LESSION')")
    public LessionResponse update(String id, LessionRequest request){
        Lession lession = lessionRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, lession);
        log.info("Update successfully.");
        return modelMapper.map(lessionRepository.save(lession), LessionResponse.class);
    }

    
    
    @PreAuthorize("hasAuthority('DELETE_LESSION')")
    public void delete(String id){
        lessionRepository.deleteById(id);
        log.info("Delete successfully.");
    }
}
