package com.farmer.be.service.mapper;

import static com.farmer.be.domain.DiagnosticTestAsserts.*;
import static com.farmer.be.domain.DiagnosticTestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiagnosticTestMapperTest {

    private DiagnosticTestMapper diagnosticTestMapper;

    @BeforeEach
    void setUp() {
        diagnosticTestMapper = new DiagnosticTestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDiagnosticTestSample1();
        var actual = diagnosticTestMapper.toEntity(diagnosticTestMapper.toDto(expected));
        assertDiagnosticTestAllPropertiesEquals(expected, actual);
    }
}
