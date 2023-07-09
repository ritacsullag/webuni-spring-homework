package com.csullagrita.school.service;

import com.csullagrita.school.api.model.StudentDto;
import com.csullagrita.school.aspect.RetryHandler;
import com.csullagrita.school.exception.SomethingWentWrongException;
import com.csullagrita.school.mapper.StudentMapper;
import com.csullagrita.school.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Component
@RequiredArgsConstructor
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CenterSystemService centerSystemService;

    @Value("${folder.profilePics}")
    private String profilePictureFolder;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(profilePictureFolder));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

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

    public void saveProfilePicture(Integer id, InputStream inputStream) {
        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        try {
            Files.copy(inputStream, getProfilePicturePath(id), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Resource getProfilePicture(Integer studentId) {
        FileSystemResource fileSystemResource = new FileSystemResource(getProfilePicturePath(studentId));
        if (!fileSystemResource.exists()) {
            logger.error("Resource is not exist");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return fileSystemResource;
    }

    public void deleteProfilePicture(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        try {
            Files.delete(getProfilePicturePath(studentId));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private Path getProfilePicturePath(Integer id) {
        return Path.of(profilePictureFolder, id.toString() + ".jpeg");
    }
}

