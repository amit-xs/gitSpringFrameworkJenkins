package com.shanInfotech.springFrameworkDbConnect.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanInfotech.springFrameworkDbConnect.entities.CourseEntity;
import com.shanInfotech.springFrameworkDbConnect.entities.TrainerEntity;
import com.shanInfotech.springFrameworkDbConnect.repositories.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TrainerService trainerService;

    public CourseService(CourseRepository courseRepository, TrainerService trainerService) {
        this.courseRepository = courseRepository;
        this.trainerService = trainerService;
    }

    @Transactional
    public CourseEntity createCourse(String courseId, String courseName, TrainerEntity trainer,
                                     int durationInDays, BigDecimal feePerParticipant) {
        if (!trainerService.exists(trainer.getTrainerId())) {
            trainerService.registerTrainer(trainer);        // parent row first
        }
        CourseEntity course = new CourseEntity(courseId, courseName, trainer,
                                               durationInDays, feePerParticipant);
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public CourseEntity getCourse(String courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CourseEntity> getCoursesOfTrainer(String trainerId) {
        return courseRepository.findByTrainerTrainerId(trainerId);
    }

    @Transactional(readOnly = true)
    public List<CourseEntity> getCoursesByExpertise(String expertise) {
        return courseRepository.findByTrainerExpertise(expertise);
    }

    @Transactional
    public void deleteCourse(String courseId) {
        courseRepository.deleteById(courseId);
    }

    @Transactional(readOnly = true)
    public long totalCourses() {
        return courseRepository.count();
    }
}
