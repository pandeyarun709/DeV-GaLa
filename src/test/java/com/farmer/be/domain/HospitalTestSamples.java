package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HospitalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Hospital getHospitalSample1() {
        return new Hospital().id(1L).name("name1").logoPath("logoPath1").createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static Hospital getHospitalSample2() {
        return new Hospital().id(2L).name("name2").logoPath("logoPath2").createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static Hospital getHospitalRandomSampleGenerator() {
        return new Hospital()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .logoPath(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
