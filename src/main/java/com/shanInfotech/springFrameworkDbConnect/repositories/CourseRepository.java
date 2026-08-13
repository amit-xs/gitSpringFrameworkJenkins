package com.shanInfotech.springFrameworkDbConnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shanInfotech.springFrameworkDbConnect.entities.CourseEntity;
@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, String> {

    // course -> trainer -> trainerId
    List<CourseEntity> findByTrainerTrainerId(String trainerId);

    // course -> trainer -> expertise
    List<CourseEntity> findByTrainerExpertise(String expertise);

    long countByTrainerTrainerId(String trainerId);
}
