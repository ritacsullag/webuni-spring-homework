package com.csullagrita.school.controller;

import com.csullagrita.school.api.CourseControllerApi;
import com.csullagrita.school.api.model.CourseDto;
import com.csullagrita.school.api.model.HistoryDataCourseDto;
import com.csullagrita.school.mapper.CourseMapper;
import com.csullagrita.school.model.Course;
import com.csullagrita.school.repository.CourseRepository;
import com.csullagrita.school.service.CourseService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseControllerApi {

    private final NativeWebRequest nativeWebRequest;
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final PageableHandlerMethodArgumentResolver pageableResolver;
    private final QuerydslPredicateArgumentResolver predicateResolver;

    //mert kell egy methodparameter, aminek megmondjuk hanyadik parametere a pageable
    public void configPageable(@SortDefault("id") Pageable pageable) {
    }

    public void configurePredicate(@QuerydslPredicate(root = Course.class) Predicate predicate) {
    }

    @Override
    public ResponseEntity<List<CourseDto>> searchCourse(Long id, String name, Boolean full, Integer page, Integer size, List<String> sort) {
        Pageable pageable = createPageable();
        Predicate predicate = createPredicate();

        boolean isFull = full == null ? false : full;
        if (isFull) {
            return ResponseEntity.ok(courseMapper.coursesToDtos(courseService.searchCourses(predicate, pageable)));
        } else {
            return ResponseEntity.ok(courseMapper.courseSummariesToDtos(courseRepository.findAll(predicate, pageable)));
        }
    }

    private Predicate createPredicate() {
        Method method;
        try {
            method = this.getClass().getMethod("configurePredicate", Predicate.class);
            MethodParameter methodParameter = new MethodParameter(method, 0);
            return (Predicate) predicateResolver.resolveArgument(methodParameter, null, nativeWebRequest, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Pageable createPageable() {
        Pageable pageable;
        Method method;
        try {
            method = this.getClass().getMethod("configPageable", Pageable.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        MethodParameter methodParameter = new MethodParameter(method, 0);
        pageable = pageableResolver.resolveArgument(methodParameter, null, nativeWebRequest, null);
        return pageable;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<List<HistoryDataCourseDto>> getCourseHistoryById(Integer id) {

        return ResponseEntity.ok(
                courseMapper.coursesHistoryToHistoryDataCourseDtos(courseService.getCourseHistoryWithRelation(id)));
    }

    @Override
    public ResponseEntity<CourseDto> getVersionAt(Integer id, OffsetDateTime at) {
        return ResponseEntity.ok(courseMapper.courseToDto(courseService.getVersionAt(id, at)));
    }

    @Override
    public ResponseEntity<CourseDto> saveChange(Integer id, CourseDto courseDto) {
        Course course = courseMapper.dtoToCourse(courseDto);
        course.setId(id);
        try {
            CourseDto savedCourseDto = courseMapper.courseToDto(courseService.update(course));
            return ResponseEntity.ok(savedCourseDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
