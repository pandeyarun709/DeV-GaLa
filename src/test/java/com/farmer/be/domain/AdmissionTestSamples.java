package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdmissionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Admission getAdmissionSample1() {
        return new Admission().id(1L).details("details1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static Admission getAdmissionSample2() {
        return new Admission().id(2L).details("details2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static Admission getAdmissionRandomSampleGenerator() {
        return new Admission()
            .id(longCount.incrementAndGet())
            .details(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
