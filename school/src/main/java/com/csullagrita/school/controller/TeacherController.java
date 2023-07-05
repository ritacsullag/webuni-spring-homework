package com.csullagrita.school.controller;

import com.csullagrita.school.dto.TeacherDto;
import com.csullagrita.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public TeacherDto getTeacherById(@PathVariable("id") int teacherId) {
        return teacherService.getStudentById(teacherId);
    }
}
