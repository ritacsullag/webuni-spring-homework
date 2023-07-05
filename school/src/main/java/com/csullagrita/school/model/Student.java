package com.csullagrita.school.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Set;

@Audited
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Data
@Cacheable
public class Student {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    @ToString.Include
    private long id;

    @ToString.Include
    private String name;

    private LocalDate dateOfBirth;

    private int semester;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    private long centralId;

    private Integer usedFreeSemester;
}
