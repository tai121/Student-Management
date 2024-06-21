package com.taipham.studentmanagement.service;

import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.UserCreationRequest;
import com.taipham.studentmanagement.dto.request.UserUpdateRequest;
import com.taipham.studentmanagement.dto.response.UserResponse;
import com.taipham.studentmanagement.entity.User;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.RoleRepository;
import com.taipham.studentmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;

    RoleRepository roleRepository;

    ModelMapper modelMapper;

    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('CREATE_USER')")
    public UserResponse create(UserCreationRequest request){
        User user = modelMapper.map(request,User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findByNameIn(request.getRoles())));
        log.info("Created successfully.");
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    public UserResponse getById(String id){
        User user = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(user, UserResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    public List<UserResponse> getAll(){
        List<User> users = userRepository.findAll();
        log.info("Retrieved successfully.");
        return users.stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public UserResponse update(String id, UserUpdateRequest request){
        // roleRepository.findAllById(null)
        User user = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, user);
        if(request.getRoles()!=null && request.getRoles().size()!=0)
            user.setRoles(new HashSet<>(roleRepository.findByNameIn(request.getRoles())));
        if(request.getPassword()!=null)
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        log.info("Updated successfully.");
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void delete(String id){
        userRepository.deleteById(id);
        log.info("Deleted successfully.");
    }
}
