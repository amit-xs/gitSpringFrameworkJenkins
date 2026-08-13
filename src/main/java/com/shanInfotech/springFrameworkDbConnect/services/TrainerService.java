package com.shanInfotech.springFrameworkDbConnect.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanInfotech.springFrameworkDbConnect.entities.TrainerEntity;
import com.shanInfotech.springFrameworkDbConnect.repositories.TrainerRepository;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Transactional
    public void registerTrainer(TrainerEntity trainer) {
        if (!trainerRepository.existsById(trainer.getTrainerId())) {
            trainerRepository.save(trainer);
        }
    }

    @Transactional(readOnly = true)
    public TrainerEntity getTrainer(String trainerId) {
        return trainerRepository.findById(trainerId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TrainerEntity> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TrainerEntity> getTrainersByExpertise(String expertise) {
        return trainerRepository.findByExpertise(expertise);
    }

    @Transactional
    public void updateTrainer(TrainerEntity trainer) {
        trainerRepository.save(trainer);
    }

    @Transactional
    public void removeTrainer(String trainerId) {
        trainerRepository.deleteById(trainerId);
    }

    @Transactional(readOnly = true)
    public boolean exists(String trainerId) {
        return trainerRepository.existsById(trainerId);
    }
}
