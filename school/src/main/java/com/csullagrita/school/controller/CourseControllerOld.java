package com.csullagrita.school.controller;

import com.csullagrita.school.api.model.CourseDto;
import com.csullagrita.school.mapper.CourseMapper;
import com.csullagrita.school.model.Course;
import com.csullagrita.school.model.HistoryData;
import com.csullagrita.school.repository.CourseRepository;
import com.csullagrita.school.service.CourseService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
//@RestController
@RequestMapping("/api/courses")
public class CourseControllerOld {

    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @GetMapping("/search")
    public List<CourseDto> searchCourse(@QuerydslPredicate(root = Course.class) Predicate predicate,
                                        @RequestParam Optional<Boolean> full,
                                        @SortDefault("id") Pageable pageable) {
        if (full.orElse(false)) {
            return courseMapper.coursesToDtos(courseService.searchCourses(predicate, pageable));
        } else {
            return courseMapper.courseSummariesToDtos(courseRepository.findAll(predicate, pageable));
        }
    }

    @GetMapping("/{id}/history")
    public List<HistoryData<CourseDto>> getCourseHistoryById(@PathVariable("id") int courseId) {

        List<HistoryData<Course>> courseHistories = courseService.getCourseHistoryWithRelation(courseId);

        List<HistoryData<CourseDto>> courseDtosWithHistory = new ArrayList<>();

        courseHistories.forEach(hd -> courseDtosWithHistory.add(new HistoryData<>(
                courseMapper.courseToDto(hd.getData()),
                hd.getRevisionType(),
                hd.getRevision(),
                hd.getDate()
        )));

        return courseDtosWithHistory;
    }

    @GetMapping(value = "/{id}/versions", params = "at")
    public ResponseEntity<CourseDto> getVersionAt(@PathVariable int id, @RequestParam OffsetDateTime at) {
        return ResponseEntity.ok(courseMapper.courseToDto(courseService.getVersionAt(id, at)));
    }


    @PutMapping("/{id}/modify")
    public ResponseEntity<CourseDto> saveChange(@PathVariable("id") int courseId, @RequestBody CourseDto courseDto) {
        Course course = courseMapper.dtoToCourse(courseDto);
        course.setId(courseId);
        try {
            CourseDto savedCourseDto = courseMapper.courseToDto(courseService.update(course));
            return ResponseEntity.ok(savedCourseDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
