package com.taipham.studentmanagement.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Double total;
    LocalDateTime createAt;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToMany
    Set<Course> courses;
}
