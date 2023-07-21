package com.csullagrita.school.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Set;

@Audited
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Cacheable
public class Teacher extends LoginUser{

    @ManyToMany(mappedBy = "teachers")
    private Set<Course> courses;

    @Override
    public UserType getUserType() {
        return UserType.TEACHER;
    }
}
