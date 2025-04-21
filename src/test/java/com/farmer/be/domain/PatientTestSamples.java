package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PatientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Patient getPatientSample1() {
        return new Patient()
            .id(1L)
            .registrationId("registrationId1")
            .name("name1")
            .phone(1L)
            .email("email1")
            .emergencyContactName("emergencyContactName1")
            .emergencyContactPhone(1L)
            .emergencyContactEmail("emergencyContactEmail1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static Patient getPatientSample2() {
        return new Patient()
            .id(2L)
            .registrationId("registrationId2")
            .name("name2")
            .phone(2L)
            .email("email2")
            .emergencyContactName("emergencyContactName2")
            .emergencyContactPhone(2L)
            .emergencyContactEmail("emergencyContactEmail2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static Patient getPatientRandomSampleGenerator() {
        return new Patient()
            .id(longCount.incrementAndGet())
            .registrationId(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .emergencyContactName(UUID.randomUUID().toString())
            .emergencyContactPhone(longCount.incrementAndGet())
            .emergencyContactEmail(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
