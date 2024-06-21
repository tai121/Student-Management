package com.taipham.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.dto.request.StudentCreationRequest;
import com.taipham.studentmanagement.dto.request.StudentUpdateRequest;
import com.taipham.studentmanagement.dto.response.StudentResponse;
import com.taipham.studentmanagement.entity.Role;
import com.taipham.studentmanagement.entity.Student;
import com.taipham.studentmanagement.entity.User;
import com.taipham.studentmanagement.exception.AppException;
import com.taipham.studentmanagement.exception.ErrorCode;
import com.taipham.studentmanagement.repository.RoleRepository;
import com.taipham.studentmanagement.repository.StudentRepository;
import com.taipham.studentmanagement.repository.UserRepository;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class StudentService {
    StudentRepository studentRepository;

    UserRepository userRepository;

    RoleRepository roleRepository;

    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('CREATE_STUDENT')")
    public StudentResponse create(StudentCreationRequest request){
        Student student = modelMapper.map(request, Student.class);
        User user = User.builder()
                        .username(request.getStudentCode())
                        .password(passwordEncoder.encode(request.getStudentCode()))
                        .backupEmail(request.getEmail())
                        .build();
        Set<Role> roles = new HashSet<>();
        Role user_role = roleRepository.findByName("USER");
        roles.add(user_role);
        user.setRoles(roles);
        userRepository.save(user);
        student.setUser(user);
        log.info("Created successfully.");
        return modelMapper.map(studentRepository.save(student), StudentResponse.class);

    }

    @PreAuthorize("hasAuthority('READ_STUDENT')")
    public StudentResponse getById(String id){
        Student student = studentRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        log.info("Retrieved successfully.");
        return modelMapper.map(student, StudentResponse.class);
    }

    @PreAuthorize("hasAuthority('READ_STUDENT')")
    public List<StudentResponse> getAll(){
        List<Student> students = studentRepository.findAll();
        log.info("Retrieved successfully.");
        return students.stream().map(student -> modelMapper.map(student, StudentResponse.class)).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_STUDENT')")
    public StudentResponse update(String id, StudentUpdateRequest request){
        Student student = studentRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.ID_DOES_NOT_EXIST));
        modelMapper.map(request, student);
        log.info("Update successfully.");
        return modelMapper.map(studentRepository.save(student), StudentResponse.class);
    }

    @PreAuthorize("hasAuthority('DELETE_STUDENT')")
    public void delete(String id){
        studentRepository.deleteById(id);
        log.info("Delete successfully.");
    }
}
