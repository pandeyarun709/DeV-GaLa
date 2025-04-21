package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LedgerItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LedgerItem getLedgerItemSample1() {
        return new LedgerItem().id(1L).description("description1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static LedgerItem getLedgerItemSample2() {
        return new LedgerItem().id(2L).description("description2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static LedgerItem getLedgerItemRandomSampleGenerator() {
        return new LedgerItem()
            .id(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
