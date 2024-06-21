package com.taipham.studentmanagement.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String lectureCode;

    @OneToMany(mappedBy  = "lecture",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude 
    @ToString.Exclude
    Set<Course> courses;
}
