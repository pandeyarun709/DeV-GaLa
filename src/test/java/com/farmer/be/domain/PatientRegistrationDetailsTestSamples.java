package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PatientRegistrationDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PatientRegistrationDetails getPatientRegistrationDetailsSample1() {
        return new PatientRegistrationDetails().id(1L).registrationId("registrationId1");
    }

    public static PatientRegistrationDetails getPatientRegistrationDetailsSample2() {
        return new PatientRegistrationDetails().id(2L).registrationId("registrationId2");
    }

    public static PatientRegistrationDetails getPatientRegistrationDetailsRandomSampleGenerator() {
        return new PatientRegistrationDetails().id(longCount.incrementAndGet()).registrationId(UUID.randomUUID().toString());
    }
}
