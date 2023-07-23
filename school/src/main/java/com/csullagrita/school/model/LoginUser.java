package com.csullagrita.school.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;


@Getter
@Setter
@SuperBuilder
@Audited
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class LoginUser {

    public enum UserType{
        TEACHER, STUDENT;
    }

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private long id;

    @ToString.Include
    private String name;

    private LocalDate dateOfBirth;

    private String username;
    private String password;

    private String facebookId;

//    Role is not an entity only a set of Strings:
//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<String> roles;

    //abstract method to define easily the type in student and teacher entity
    public abstract UserType getUserType();
}
