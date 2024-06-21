package com.taipham.studentmanagement.dto.request;

import java.util.List;

import com.taipham.studentmanagement.validator.ConstantNameConstrain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @ConstantNameConstrain(message = "CONSTAIN_NAME_EXCEPTION")
    String name;
    String description;

    List<String> permissions;
}
