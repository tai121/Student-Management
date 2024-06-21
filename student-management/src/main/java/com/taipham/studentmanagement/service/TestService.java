package com.taipham.studentmanagement.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.taipham.studentmanagement.entity.Abc;
import com.taipham.studentmanagement.repository.AbcRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // public void saveUser(Abc abc) {
    //     redisTemplate.opsForHash().put("ABC", abc.getId(), abc);
    // }

    // public Abc getUser(String id) {
    //     return (Abc) redisTemplate.opsForHash().get("ABC", id);
    // }

    // public void deleteUser(String id) {
    //     redisTemplate.opsForHash().delete("ABC", id);
    // }
    @Autowired
    private AbcRepository abcRepository;

    public void create(Abc abc){
        abcRepository.save(abc);
        String key = "Abc:" + abc.getId();
        redisTemplate.expire(key, 2000, TimeUnit.SECONDS);
    }

    public List<Abc> getAll(){
        return (List<Abc>) abcRepository.findAll();
    }
}
