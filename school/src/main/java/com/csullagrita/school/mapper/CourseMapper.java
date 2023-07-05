package com.csullagrita.school.mapper;

import com.csullagrita.school.dto.CourseDto;
import com.csullagrita.school.model.Course;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto courseToDto(Course course);

    @Named("summary")
    @Mapping(ignore = true, target = "teachers")
    @Mapping(ignore = true, target = "students")
    CourseDto courseSummaryToDto(Course course);

    Course dtoToCourse(CourseDto courseDto);

    List<CourseDto> coursesToDtos(Iterable<Course> courses);

    @IterableMapping(qualifiedByName = "summary")
    List<CourseDto> courseSummariesToDtos(Iterable<Course> findAll);

    default OffsetDateTime dateToOffsetDateTime(Date date) {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.of("Z"));
    }
}
