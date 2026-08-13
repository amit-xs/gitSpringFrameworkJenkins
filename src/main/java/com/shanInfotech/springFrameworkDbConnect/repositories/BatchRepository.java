package com.shanInfotech.springFrameworkDbConnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shanInfotech.springFrameworkDbConnect.entities.BatchEntity;

public interface BatchRepository extends JpaRepository<BatchEntity, String> {

    // batch -> course -> courseId
    List<BatchEntity> findByCourseCourseId(String courseId);

    // batch -> course -> trainer -> trainerId  (three levels)
    List<BatchEntity> findByCourseTrainerTrainerId(String trainerId);

    List<BatchEntity> findByMode(String mode);

    long countByCourseCourseId(String courseId);
}
