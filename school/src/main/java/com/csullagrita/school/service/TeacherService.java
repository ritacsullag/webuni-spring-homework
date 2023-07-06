package com.csullagrita.school.service;

import com.csullagrita.school.api.model.TeacherDto;
import com.csullagrita.school.mapper.TeacherMapper;
import com.csullagrita.school.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TeacherService {


    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherDto getStudentById(int teacherId) {
//        return teacherMapper.teacherToDto(teacherRepository.findById(teacherId)
        return teacherMapper.map(teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
