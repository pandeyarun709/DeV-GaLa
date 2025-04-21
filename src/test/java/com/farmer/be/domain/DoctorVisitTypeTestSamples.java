package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DoctorVisitTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DoctorVisitType getDoctorVisitTypeSample1() {
        return new DoctorVisitType().id(1L).createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static DoctorVisitType getDoctorVisitTypeSample2() {
        return new DoctorVisitType().id(2L).createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static DoctorVisitType getDoctorVisitTypeRandomSampleGenerator() {
        return new DoctorVisitType()
            .id(longCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
