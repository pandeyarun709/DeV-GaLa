package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedicineDoseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MedicineDose getMedicineDoseSample1() {
        return new MedicineDose().id(1L).frequency("frequency1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static MedicineDose getMedicineDoseSample2() {
        return new MedicineDose().id(2L).frequency("frequency2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static MedicineDose getMedicineDoseRandomSampleGenerator() {
        return new MedicineDose()
            .id(longCount.incrementAndGet())
            .frequency(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
