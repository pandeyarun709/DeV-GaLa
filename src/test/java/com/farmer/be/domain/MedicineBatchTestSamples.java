package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedicineBatchTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MedicineBatch getMedicineBatchSample1() {
        return new MedicineBatch()
            .id(1L)
            .batchNo("batchNo1")
            .quantity(1L)
            .storageLocation("storageLocation1")
            .rackNo("rackNo1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static MedicineBatch getMedicineBatchSample2() {
        return new MedicineBatch()
            .id(2L)
            .batchNo("batchNo2")
            .quantity(2L)
            .storageLocation("storageLocation2")
            .rackNo("rackNo2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static MedicineBatch getMedicineBatchRandomSampleGenerator() {
        return new MedicineBatch()
            .id(longCount.incrementAndGet())
            .batchNo(UUID.randomUUID().toString())
            .quantity(longCount.incrementAndGet())
            .storageLocation(UUID.randomUUID().toString())
            .rackNo(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
