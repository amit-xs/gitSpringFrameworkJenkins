package com.shanInfotech.springFrameworkDbConnect.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity {

    @Id
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    // MANY courses are delivered by ONE trainer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id", nullable = false)
    private TrainerEntity trainer;

    @Column(name = "duration_in_days", nullable = false)
    private int durationInDays;

    @Column(name = "fee_per_participant", nullable = false)
    private BigDecimal feePerParticipant;

    protected CourseEntity() {          // required by JPA
    }

    public CourseEntity(String courseId, String courseName, TrainerEntity trainer,
                        int durationInDays, BigDecimal feePerParticipant) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.trainer = trainer;
        this.durationInDays = durationInDays;
        this.feePerParticipant = feePerParticipant;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public TrainerEntity getTrainer() {
        return trainer;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public BigDecimal getFeePerParticipant() {
        return feePerParticipant;
    }

    public void setFeePerParticipant(BigDecimal feePerParticipant) {
        this.feePerParticipant = feePerParticipant;
    }

    @Override
    public String toString() {
        return courseId + " | " + courseName
                + " | " + trainer.getTrainerName()
                + " | " + durationInDays + " days"
                + " | Rs." + feePerParticipant + " per participant";
    }
}
