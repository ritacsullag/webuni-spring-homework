package com.csullagrita.school.controller;

import com.csullagrita.school.model.CourseStat;
import com.csullagrita.school.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    Logger logger = LoggerFactory.getLogger(ReportController.class);

    @GetMapping("/averageSemesterForStudents")
    @Async
    public CompletableFuture<List<CourseStat>> getSemesterReport() {
        logger.info(Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        return reportService.getSemesterReport();
    }
}
