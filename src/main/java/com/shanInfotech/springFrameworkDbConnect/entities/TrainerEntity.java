package com.shanInfotech.springFrameworkDbConnect.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trainer")
public class TrainerEntity {

    @Id
    @Column(name = "trainer_id")
    private String trainerId;

    @Column(name = "trainer_name", nullable = false)
    private String trainerName;

    @Column(nullable = false)
    private String expertise;

    @Column(nullable = false)
    private String city;

    protected TrainerEntity() {          // required by JPA
    }

    public TrainerEntity(String trainerId, String trainerName, String expertise, String city) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.expertise = expertise;
        this.city = city;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return trainerName + " (" + trainerId + ", " + expertise + ", " + city + ")";
    }
}
