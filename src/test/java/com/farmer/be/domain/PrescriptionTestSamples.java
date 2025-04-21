package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PrescriptionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prescription getPrescriptionSample1() {
        return new Prescription()
            .id(1L)
            .history("history1")
            .compliant("compliant1")
            .otherVital("otherVital1")
            .description("description1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static Prescription getPrescriptionSample2() {
        return new Prescription()
            .id(2L)
            .history("history2")
            .compliant("compliant2")
            .otherVital("otherVital2")
            .description("description2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static Prescription getPrescriptionRandomSampleGenerator() {
        return new Prescription()
            .id(longCount.incrementAndGet())
            .history(UUID.randomUUID().toString())
            .compliant(UUID.randomUUID().toString())
            .otherVital(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
