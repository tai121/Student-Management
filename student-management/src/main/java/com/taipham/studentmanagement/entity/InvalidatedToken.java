package com.taipham.studentmanagement.entity;
import java.util.Date;

import org.springframework.data.redis.core.RedisHash;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash("InvalidatedToken")
public class InvalidatedToken {
    String id;
    Date expiryTime;
}
