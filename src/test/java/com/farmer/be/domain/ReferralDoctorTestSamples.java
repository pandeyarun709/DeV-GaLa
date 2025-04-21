package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReferralDoctorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ReferralDoctor getReferralDoctorSample1() {
        return new ReferralDoctor()
            .id(1L)
            .name("name1")
            .phone(1L)
            .email("email1")
            .registrationNo("registrationNo1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static ReferralDoctor getReferralDoctorSample2() {
        return new ReferralDoctor()
            .id(2L)
            .name("name2")
            .phone(2L)
            .email("email2")
            .registrationNo("registrationNo2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static ReferralDoctor getReferralDoctorRandomSampleGenerator() {
        return new ReferralDoctor()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .registrationNo(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
