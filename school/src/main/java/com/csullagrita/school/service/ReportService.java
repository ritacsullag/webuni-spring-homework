package com.csullagrita.school.service;

import com.csullagrita.school.model.CourseStat;
import com.csullagrita.school.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CourseRepository courseRepository;

    public CompletableFuture<List<CourseStat>> getSemesterReport() {
        return CompletableFuture.completedFuture(courseRepository.getCourseStats());
    }
}
