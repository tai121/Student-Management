package com.taipham.studentmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taipham.studentmanagement.entity.Abc;
import com.taipham.studentmanagement.service.TestService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestController {

    
    TestService testService;

    @PostMapping
    public String create(@RequestBody Abc abc){
        // testService.saveUser(abc);
        testService.create(abc);
        return "oke";
    }

    // @GetMapping("/{id}")
    // public Abc getMethodName(@PathVariable String id) {
    //     return testService.getUser(id);
    // }

    @GetMapping
    public List<Abc> getAll() {
        return testService.getAll();
    }
    

    // @DeleteMapping("/{id}")
    // public String delete(@PathVariable String id){
    //     testService.deleteUser(id);
    //     return "oke";
    // }
    
}
