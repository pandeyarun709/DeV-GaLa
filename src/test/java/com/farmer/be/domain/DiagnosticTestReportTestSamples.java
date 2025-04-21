package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DiagnosticTestReportTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DiagnosticTestReport getDiagnosticTestReportSample1() {
        return new DiagnosticTestReport()
            .id(1L)
            .description("description1")
            .signedBy("signedBy1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static DiagnosticTestReport getDiagnosticTestReportSample2() {
        return new DiagnosticTestReport()
            .id(2L)
            .description("description2")
            .signedBy("signedBy2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static DiagnosticTestReport getDiagnosticTestReportRandomSampleGenerator() {
        return new DiagnosticTestReport()
            .id(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .signedBy(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
