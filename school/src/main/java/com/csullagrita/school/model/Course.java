package com.csullagrita.school.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.Set;

@Audited
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@NamedEntityGraph(
        name = "Course.students",
        attributeNodes = @NamedAttributeNode("students")
)
@NamedEntityGraph(
        name = "Course.teachers",
        attributeNodes = @NamedAttributeNode("teachers")
)
public class Course {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private long id;

    @ToString.Include
    private String name;

    @ManyToMany
    private Set<Student> students;

    @ManyToMany
    private Set<Teacher> teachers;

}
