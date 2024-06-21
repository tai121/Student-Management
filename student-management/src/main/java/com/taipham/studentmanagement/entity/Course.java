package com.taipham.studentmanagement.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    LocalDate registrationStartAt;
    LocalDate registrationEndAt;
    LocalDate courseStartAt;
    LocalDate courseEndAt;
    Double price;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    @EqualsAndHashCode.Exclude 
    @ToString.Exclude
    Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @EqualsAndHashCode.Exclude 
    @ToString.Exclude
    Instructor instructor;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    Set<Lession> lessions;

    @ManyToMany
    Set<Registration> payments;
}
