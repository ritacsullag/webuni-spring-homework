package com.csullagrita.school.service;

import com.csullagrita.school.model.*;
import com.csullagrita.school.repository.CourseRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
//    @PersistenceContext
//    private EntityManager entityManager;

    @Transactional
    @Cacheable("pagableCoursesWithRelations")
    public List<Course> searchCourses(Predicate predicate, Pageable pageable) {

        Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
        BooleanExpression courseIdInPredicate = QCourse.course.in(coursePage.getContent());

        List<Course> courses = courseRepository.findAll(courseIdInPredicate, "Course.students", Sort.unsorted());
        courses = courseRepository.findAll(courseIdInPredicate, "Course.teachers", pageable.getSort());
        return courses;
    }

    @SuppressWarnings({"rawTypes", "unchecked"})
    @Transactional
    public List<HistoryData<Course>> getCourseHistoryWithRelation(long courseId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader
                .createQuery()
                .forRevisionsOfEntity(Course.class, false, true)
                .add(AuditEntity.property("id").eq(courseId))
                .getResultList()
                .stream()
                .map(o -> {
                    Object[] objArray = (Object[]) o;
                    DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) objArray[1];
                    Course course = (Course) objArray[0];
                    course.getStudents().forEach(Student::getName);
                    course.getTeachers().forEach(Teacher::getName);

                    return new HistoryData<>(
                            course,
                            (RevisionType) objArray[2],
                            revisionEntity.getId(),
                            revisionEntity.getRevisionDate()
                    );
                }).toList();
    }

    @Transactional
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Course getVersionAt(int id, OffsetDateTime when) {
        long epochMillis = when.toInstant().toEpochMilli();
        AuditProperty<Object> timestampProperty = AuditEntity.revisionProperty("timestamp");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List resultList = AuditReaderFactory.get(entityManager)
                .createQuery()
                .forRevisionsOfEntity(Course.class, true, false)
                .add(AuditEntity.property("id").eq(id))
                .add(timestampProperty.le(epochMillis))
                .addOrder(timestampProperty.desc())
                .setMaxResults(1)
                .getResultList();

        if(!resultList.isEmpty()) {
            Course course = (Course) resultList.get(0);
            course.getStudents().size();
            course.getTeachers().size();
            return course;
        }

        return null;
    }

    @Transactional
    public Course update(Course course) {
        if (courseRepository.existsById((int) course.getId())) {
            return courseRepository.save(course);
        } else
            throw new NoSuchElementException();
    }
}
