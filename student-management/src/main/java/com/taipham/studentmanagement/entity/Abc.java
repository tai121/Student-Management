package com.taipham.studentmanagement.entity;

import java.time.LocalDate;

import org.springframework.data.redis.core.RedisHash;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash("Abc")
public class Abc {
    String id;
    String name;
    LocalDate date;
}
