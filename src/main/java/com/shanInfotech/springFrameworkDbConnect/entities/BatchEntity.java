package com.shanInfotech.springFrameworkDbConnect.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "training_batch")
public class BatchEntity {

    @Id
    @Column(name = "batch_code")
    private String batchCode;

    // MANY batches belong to ONE course
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "seats_booked", nullable = false)
    private int seatsBooked;

    @Column(nullable = false)
    private String mode;                 // ONLINE or CLASSROOM

    @Column(name = "total_revenue", nullable = false)
    private BigDecimal totalRevenue;

    protected BatchEntity() {            // required by JPA
    }

    public BatchEntity(String batchCode, CourseEntity course, LocalDate startDate,
                       int seatsBooked, String mode) {
        this.batchCode = batchCode;
        this.course = course;
        this.startDate = startDate;
        this.seatsBooked = seatsBooked;
        this.mode = mode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public String getMode() {
        return mode;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    @Override
    public String toString() {
        return batchCode + " | " + course.getCourseName()
                + " | " + course.getTrainer().getTrainerName()
                + " | " + startDate
                + " | " + seatsBooked + " seats"
                + " | " + mode
                + " | Rs." + totalRevenue;
    }
}
