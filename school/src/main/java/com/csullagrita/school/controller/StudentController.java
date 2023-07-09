package com.csullagrita.school.controller;

import com.csullagrita.school.api.StudentControllerApi;
import com.csullagrita.school.api.model.StudentDto;
import com.csullagrita.school.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Void> deleteProfilePicture(Integer id) {
        studentService.deleteProfilePicture(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Resource> getProfilePicture(Integer id) {
        return ResponseEntity.ok(studentService.getProfilePicture(id));
    }

    @Override
    public ResponseEntity<StudentDto> getStudentById(Integer id) {
        return ResponseEntity.ok(studentService.getStudentById(id));

    }

    @Override
    public ResponseEntity<Void> uploadProfilePicture(Integer id, MultipartFile content) {
        try {
            studentService.saveProfilePicture(id, content.getInputStream());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
