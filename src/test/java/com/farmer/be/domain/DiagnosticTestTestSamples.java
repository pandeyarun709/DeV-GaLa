package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DiagnosticTestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DiagnosticTest getDiagnosticTestSample1() {
        return new DiagnosticTest().id(1L).name("name1").phone(1L).email("email1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static DiagnosticTest getDiagnosticTestSample2() {
        return new DiagnosticTest().id(2L).name("name2").phone(2L).email("email2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static DiagnosticTest getDiagnosticTestRandomSampleGenerator() {
        return new DiagnosticTest()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
