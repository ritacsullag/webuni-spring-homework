package com.csullagrita.school.mapper;

import com.csullagrita.school.dto.StudentDto;
import com.csullagrita.school.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    //if you are using builder you can map()
    StudentDto map(Student student);
}
