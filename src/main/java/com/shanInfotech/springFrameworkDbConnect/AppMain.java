package com.shanInfotech.springFrameworkDbConnect;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.shanInfotech.springFrameworkDbConnect.config.SpringDataJpaConfig;
import com.shanInfotech.springFrameworkDbConnect.entities.BatchEntity;
import com.shanInfotech.springFrameworkDbConnect.entities.CourseEntity;
import com.shanInfotech.springFrameworkDbConnect.entities.TrainerEntity;
import com.shanInfotech.springFrameworkDbConnect.services.BatchService;
import com.shanInfotech.springFrameworkDbConnect.services.CourseService;
import com.shanInfotech.springFrameworkDbConnect.services.TrainerService;

public class AppMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);

        TrainerService trainerService = context.getBean(TrainerService.class);
        CourseService courseService = context.getBean(CourseService.class);
        BatchService batchService = context.getBean(BatchService.class);

        TrainerEntity shanmukh = new TrainerEntity("TRN01", "Shanmukh Inamdar", "Java", "Bengaluru");
        TrainerEntity anitha = new TrainerEntity("TRN02", "Anitha Reddy", "Cloud", "Hyderabad");

        // CREATE - courses
        System.out.println("\n===== Creating courses =====");
        courseService.createCourse("CRS101", "Core Java", shanmukh, 5, new BigDecimal("8000.00"));
        courseService.createCourse("CRS102", "Spring Framework", shanmukh, 4, new BigDecimal("9000.00"));
        courseService.createCourse("CRS103", "AWS Essentials", anitha, 3, new BigDecimal("7500.00"));

        // CREATE - batches
        System.out.println("\n===== Scheduling batches =====");
        batchService.scheduleBatch("BAT001", "CRS101", LocalDate.of(2026, 8, 3), 12, "CLASSROOM");
        batchService.scheduleBatch("BAT002", "CRS101", LocalDate.of(2026, 9, 7), 25, "ONLINE");
        batchService.scheduleBatch("BAT003", "CRS102", LocalDate.of(2026, 8, 17), 18, "ONLINE");
        batchService.scheduleBatch("BAT004", "CRS103", LocalDate.of(2026, 8, 24), 30, "CLASSROOM");

        // READ - all batches
        System.out.println("\n===== All batches =====");
        List<BatchEntity> allBatches = batchService.getAllBatches();
        for (BatchEntity batch : allBatches) {
            System.out.println("   " + batch);
        }

        // READ - batches of one course
        System.out.println("\n===== Batches of CRS101 =====");
        List<BatchEntity> courseBatches = batchService.getBatchesOfCourse("CRS101");
        for (BatchEntity batch : courseBatches) {
            System.out.println("   " + batch);
        }

        // READ - three-level navigation: batch -> course -> trainer
        System.out.println("\n===== Batches handled by TRN01 =====");
        List<BatchEntity> trainerBatches = batchService.getBatchesOfTrainer("TRN01");
        for (BatchEntity batch : trainerBatches) {
            System.out.println("   " + batch);
        }

        // READ - by mode
        System.out.println("\n===== ONLINE batches =====");
        List<BatchEntity> onlineBatches = batchService.getBatchesByMode("ONLINE");
        for (BatchEntity batch : onlineBatches) {
            System.out.println("   " + batch);
        }

        // READ - courses by trainer expertise
        System.out.println("\n===== Courses in Cloud =====");
        List<CourseEntity> cloudCourses = courseService.getCoursesByExpertise("Cloud");
        for (CourseEntity course : cloudCourses) {
            System.out.println("   " + course);
        }

        // Revenue
        System.out.println("\n===== Revenue =====");
        System.out.println("   TRN01 total revenue : Rs."
                + batchService.totalRevenueOfTrainer("TRN01"));

        // UPDATE
        System.out.println("\n===== Updating trainer city =====");
        anitha.setCity("Bengaluru");
        trainerService.updateTrainer(anitha);
        System.out.println("   " + trainerService.getTrainer("TRN02"));

        // DELETE
        System.out.println("\n===== Cancelling BAT004 =====");
        batchService.cancelBatch("BAT004");

        // COUNT
        System.out.println("\n===== Summary =====");
        System.out.println("   Total trainers : " + trainerService.getAllTrainers().size());
        System.out.println("   Total courses  : " + courseService.totalCourses());
        System.out.println("   Total batches  : " + batchService.totalBatches());

        context.close();
    }
}