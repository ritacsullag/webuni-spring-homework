package com.csullagrita.school.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentDto {
    private long id;
    private String name;
    private LocalDate dateOfBirth;

    private int semester;
    private long centralId;

    private int usedFreeSemester;
}
