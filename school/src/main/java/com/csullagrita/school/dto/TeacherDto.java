package com.csullagrita.school.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TeacherDto {
    private long id;
    private String name;
    private LocalDate dateOfBirth;

}
