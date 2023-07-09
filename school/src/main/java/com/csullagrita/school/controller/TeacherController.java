package com.csullagrita.school.controller;

import com.csullagrita.school.api.TeacherControllerApi;
import com.csullagrita.school.api.model.TeacherDto;
import com.csullagrita.school.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TeacherController implements TeacherControllerApi {

    private final NativeWebRequest nativeWebRequest;
    private final TeacherService teacherService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<TeacherDto> getTeacherById(Integer id) {
        return ResponseEntity.ok(teacherService.getStudentById(id));
    }
}
