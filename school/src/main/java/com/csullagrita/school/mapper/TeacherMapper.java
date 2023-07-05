package com.csullagrita.school.mapper;

import com.csullagrita.school.dto.CourseDto;
import com.csullagrita.school.dto.TeacherDto;
import com.csullagrita.school.model.Course;
import com.csullagrita.school.model.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDto map(Teacher teacher);

    Course courseDtoToCourse(CourseDto courseDto);

}