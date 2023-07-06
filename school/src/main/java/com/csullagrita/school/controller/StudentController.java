package com.csullagrita.school.controller;

import com.csullagrita.school.api.StudentControllerApi;
import com.csullagrita.school.api.model.StudentDto;
import com.csullagrita.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi {

    private final NativeWebRequest nativeWebRequest;
    private final StudentService studentService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<StudentDto> getStudentById(Integer id) {
        return ResponseEntity.ok(studentService.getStudentById(id));

    }
}
