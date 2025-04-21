package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bed getBedSample1() {
        return new Bed().id(1L).floor(1L).roomNo(1L).createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static Bed getBedSample2() {
        return new Bed().id(2L).floor(2L).roomNo(2L).createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static Bed getBedRandomSampleGenerator() {
        return new Bed()
            .id(longCount.incrementAndGet())
            .floor(longCount.incrementAndGet())
            .roomNo(longCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
