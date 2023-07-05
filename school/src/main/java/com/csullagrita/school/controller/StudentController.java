package com.csullagrita.school.controller;

import com.csullagrita.school.dto.StudentDto;
import com.csullagrita.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;


    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable("id") int studentId) {
        return studentService.getStudentById(studentId);
    }
}
