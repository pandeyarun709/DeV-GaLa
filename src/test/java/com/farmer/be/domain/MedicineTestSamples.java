package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedicineTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Medicine getMedicineSample1() {
        return new Medicine()
            .id(1L)
            .name("name1")
            .genericMolecule("genericMolecule1")
            .description("description1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static Medicine getMedicineSample2() {
        return new Medicine()
            .id(2L)
            .name("name2")
            .genericMolecule("genericMolecule2")
            .description("description2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static Medicine getMedicineRandomSampleGenerator() {
        return new Medicine()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .genericMolecule(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
