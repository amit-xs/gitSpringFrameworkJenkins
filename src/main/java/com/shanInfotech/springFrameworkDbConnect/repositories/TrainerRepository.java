package com.shanInfotech.springFrameworkDbConnect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shanInfotech.springFrameworkDbConnect.entities.TrainerEntity;

public interface TrainerRepository extends JpaRepository<TrainerEntity, String> {

    List<TrainerEntity> findByExpertise(String expertise);

    List<TrainerEntity> findByCity(String city);
}
