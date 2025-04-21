package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class QualificationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Qualification getQualificationSample1() {
        return new Qualification().id(1L).name("name1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static Qualification getQualificationSample2() {
        return new Qualification().id(2L).name("name2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static Qualification getQualificationRandomSampleGenerator() {
        return new Qualification()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
