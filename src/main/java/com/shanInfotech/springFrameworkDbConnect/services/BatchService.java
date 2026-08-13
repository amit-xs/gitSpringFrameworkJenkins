package com.shanInfotech.springFrameworkDbConnect.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanInfotech.springFrameworkDbConnect.entities.BatchEntity;
import com.shanInfotech.springFrameworkDbConnect.entities.CourseEntity;
import com.shanInfotech.springFrameworkDbConnect.repositories.BatchRepository;

@Service
public class BatchService {

    private static final BigDecimal GST_RATE = new BigDecimal("0.18");
    private static final BigDecimal CORPORATE_DISCOUNT = new BigDecimal("0.10");
    private static final int DISCOUNT_SEAT_LIMIT = 20;

    private final BatchRepository batchRepository;
    private final CourseService courseService;

    public BatchService(BatchRepository batchRepository, CourseService courseService) {
        this.batchRepository = batchRepository;
        this.courseService = courseService;
    }

    // base = fee per participant x seats
    // 10% corporate discount when seats >= 20
    // 18% GST on the discounted amount
    @Transactional
    public BatchEntity scheduleBatch(String batchCode, String courseId,
                                     LocalDate startDate, int seatsBooked, String mode) {

        CourseEntity course = courseService.getCourse(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }

        BatchEntity batch = new BatchEntity(batchCode, course, startDate, seatsBooked, mode);

        BigDecimal base = course.getFeePerParticipant()
                                .multiply(new BigDecimal(seatsBooked));

        BigDecimal discount = BigDecimal.ZERO;
        if (seatsBooked >= DISCOUNT_SEAT_LIMIT) {
            discount = base.multiply(CORPORATE_DISCOUNT);
        }

        BigDecimal afterDiscount = base.subtract(discount);
        BigDecimal gst = afterDiscount.multiply(GST_RATE);
        BigDecimal total = afterDiscount.add(gst).setScale(2, RoundingMode.HALF_UP);

        batch.setTotalRevenue(total);
        return batchRepository.save(batch);
    }

    @Transactional(readOnly = true)
    public List<BatchEntity> getAllBatches() {
        return batchRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BatchEntity> getBatchesOfCourse(String courseId) {
        return batchRepository.findByCourseCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public List<BatchEntity> getBatchesOfTrainer(String trainerId) {
        return batchRepository.findByCourseTrainerTrainerId(trainerId);
    }

    @Transactional(readOnly = true)
    public List<BatchEntity> getBatchesByMode(String mode) {
        return batchRepository.findByMode(mode);
    }

    // classical loop, no streams
    @Transactional(readOnly = true)
    public BigDecimal totalRevenueOfTrainer(String trainerId) {
        List<BatchEntity> batches = batchRepository.findByCourseTrainerTrainerId(trainerId);
        BigDecimal total = BigDecimal.ZERO;
        for (BatchEntity batch : batches) {
            total = total.add(batch.getTotalRevenue());
        }
        return total;
    }

    @Transactional
    public void cancelBatch(String batchCode) {
        batchRepository.deleteById(batchCode);
    }

    @Transactional(readOnly = true)
    public long totalBatches() {
        return batchRepository.count();
    }
}
