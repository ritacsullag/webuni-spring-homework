package com.csullagrita.school.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseDto {
    private long id;
    private String name;

    private List<TeacherDto> teachers;
    private List<StudentDto> students;
}

