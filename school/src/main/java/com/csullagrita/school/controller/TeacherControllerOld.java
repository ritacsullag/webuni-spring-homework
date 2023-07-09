package com.csullagrita.school.controller;

import com.csullagrita.school.api.model.TeacherDto;
import com.csullagrita.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherControllerOld {

    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable("id") int teacherId) {
        return ResponseEntity.ok(teacherService.getStudentById(teacherId));
    }
}
