package com.csullagrita.school.service;

import com.csullagrita.school.model.Course;
import com.csullagrita.school.model.Student;
import com.csullagrita.school.model.Teacher;
import com.csullagrita.school.repository.CourseRepository;
import com.csullagrita.school.repository.StudentRepository;
import com.csullagrita.school.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class DbInitService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void deleteData() {
        courseRepository.deleteAll();
        teacherRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Transactional
    public void deleteAudData() {
        jdbcTemplate.update("DELETE FROM student_aud");
        jdbcTemplate.update("DELETE FROM teacher_aud");
        jdbcTemplate.update("DELETE FROM course_aud");
    }

    @Transactional
    public void addInitData() {
        Student student1 = studentRepository.save(Student.builder().name("Bela").dateOfBirth(LocalDate.of(2020, 8, 17)).semester(1).usedFreeSemester(1).centralId(3456L).balance(30000).build());
        Student student2 = studentRepository.save(Student.builder().name("Lara").dateOfBirth(LocalDate.of(2021, 1, 21)).semester(1).usedFreeSemester(2).centralId(3457L).balance(25000).build());
        Student student3 = studentRepository.save(Student.builder().name("Manna").dateOfBirth(LocalDate.of(2020, 12, 19)).semester(2).usedFreeSemester(3).centralId(3476L).balance(20000).build());

        Teacher teacher1 = teacherRepository.save(Teacher.builder().name("Klara").dateOfBirth(LocalDate.of(2022, 6, 10)).build());
        Teacher teacher2 = teacherRepository.save(Teacher.builder().name("Karoly").dateOfBirth(LocalDate.of(2000, 6, 10)).build());
        Teacher teacher3 = teacherRepository.save(Teacher.builder().name("Liza").dateOfBirth(LocalDate.of(1984, 3, 10)).build());

        courseRepository.save(Course.builder().name("fizika").students(new HashSet<>(Arrays.asList(student2, student1))).teachers(new HashSet<>(List.of(teacher2))).build());
        courseRepository.save(Course.builder().name("math").students(new HashSet<>(Arrays.asList(student2, student1, student3))).teachers(Set.of(teacher2, teacher1)).build());
        courseRepository.save(Course.builder().name("english").students(new HashSet<>(List.of(student3))).teachers(Set.of(teacher3, teacher1, teacher2)).build());
    }
}
