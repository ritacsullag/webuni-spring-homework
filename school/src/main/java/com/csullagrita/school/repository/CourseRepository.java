package com.csullagrita.school.repository;

import com.csullagrita.school.model.Course;
import com.csullagrita.school.model.CourseStat;
import com.csullagrita.school.model.QCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends
        JpaRepository<Course, Integer>,
        QuerydslBinderCustomizer<QCourse>,
        QuerydslWithEntityGraphRepository<Course, Integer>,
        QuerydslPredicateExecutor<Course> {

    @Override
    default void customize(QuerydslBindings bindings, QCourse course) {
//        ide kerulnek a megadott szurok
//        Kurzusneve (prefixes keresés, kis/nagybetűnemszámít)
        bindings.bind(course.name).first((path, value) -> path.startsWithIgnoreCase(value));
//        Kurzusid-je (pontosegyezés)
        bindings.bind(course.id).first((path, value) -> path.eq(value));
//        A kurzustoktatótanárneve (prefixes keresés, kis/nagybetűnemszámít)
        bindings.bind(course.teachers.any().name).first((path, value) -> path.startsWithIgnoreCase(value));
//        A kurzusrajelentkezettdiákid-je (pontosegyezés)
        bindings.bind(course.students.any().id).first((path, value) -> path.eq(value));
//        A kurzusrajelentkezettdiákaktuálisszemeszterénekszáma(tól/igjellegűkeresés)
        bindings.bind(course.students.any().semester).all((path, values) -> {
            if (values.size() != 2) {
                return Optional.empty();
            }
            Iterator<? extends Integer> iterator = values.iterator();
            int from = iterator.next();
            int to = iterator.next();
            return Optional.of(path.between(from, to));
        });
    }

    @Query("SELECT c.id as courseId, c.name as courseName, AVG(s.semester) as averageSemesterOfStudents "
            + "FROM Course c LEFT JOIN c.students s "
            + "GROUP BY c")
    List<CourseStat> getCourseStats();
}
