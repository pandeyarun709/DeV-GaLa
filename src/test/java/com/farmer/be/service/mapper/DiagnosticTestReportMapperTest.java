package com.farmer.be.service.mapper;

import static com.farmer.be.domain.DiagnosticTestReportAsserts.*;
import static com.farmer.be.domain.DiagnosticTestReportTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiagnosticTestReportMapperTest {

    private DiagnosticTestReportMapper diagnosticTestReportMapper;

    @BeforeEach
    void setUp() {
        diagnosticTestReportMapper = new DiagnosticTestReportMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDiagnosticTestReportSample1();
        var actual = diagnosticTestReportMapper.toEntity(diagnosticTestReportMapper.toDto(expected));
        assertDiagnosticTestReportAllPropertiesEquals(expected, actual);
    }
}
