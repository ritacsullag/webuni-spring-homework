package com.csullagrita.school.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Set;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Cacheable
public class Teacher {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private long id;

    @ToString.Include
    private String name;

    private LocalDate dateOfBirth;

    @ManyToMany(mappedBy = "teachers")
    private Set<Course> courses;
}
