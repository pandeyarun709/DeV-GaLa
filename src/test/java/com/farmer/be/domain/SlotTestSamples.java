package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SlotTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Slot getSlotSample1() {
        return new Slot()
            .id(1L)
            .startTimeHour(1L)
            .startTimeMin(1L)
            .endTimeHour(1L)
            .endTimeMin(1L)
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static Slot getSlotSample2() {
        return new Slot()
            .id(2L)
            .startTimeHour(2L)
            .startTimeMin(2L)
            .endTimeHour(2L)
            .endTimeMin(2L)
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static Slot getSlotRandomSampleGenerator() {
        return new Slot()
            .id(longCount.incrementAndGet())
            .startTimeHour(longCount.incrementAndGet())
            .startTimeMin(longCount.incrementAndGet())
            .endTimeHour(longCount.incrementAndGet())
            .endTimeMin(longCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
