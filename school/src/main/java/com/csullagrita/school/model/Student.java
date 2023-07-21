package com.csullagrita.school.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Set;

@Audited
@SuperBuilder /*use the super class fields as well*/
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Data
@Cacheable
public class Student extends LoginUser{

    private int semester;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    private long centralId;

    private Integer usedFreeSemester;

    private int balance;

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }
}
