package com.csullagrita.school.service;

import com.csullagrita.school.api.model.StudentDto;
import com.csullagrita.school.aspect.RetryHandler;
import com.csullagrita.school.exception.SomethingWentWrongException;
import com.csullagrita.school.mapper.StudentMapper;
import com.csullagrita.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Component
@RequiredArgsConstructor
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CenterSystemService centerSystemService;

    public StudentDto getStudentById(int studentId) {
//        return studentMapper.studentToDto(studentRepository.findById(studentId)
        return studentMapper.map(studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @RetryHandler
    @Transactional
    @Scheduled(cron = "${update.student.used.free.semester.cron}")
    @SchedulerLock(name = "updateUsedSemestersForStudent")
    public void updateUsedSemestersForStudents() {
        logger.info("updateUsedSemestersForStudents was called");
        studentRepository.findAll().forEach(
                student -> {
                    try {
                        student.setUsedFreeSemester(centerSystemService.getUsedFreeSemester(student.getCentralId()));
                        studentRepository.save(student);
                    } catch (SomethingWentWrongException e) {
                        logger.warn(e.getMessage());
                    }
                }
        );

    }
}

