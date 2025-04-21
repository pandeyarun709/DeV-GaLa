package com.farmer.be.service.mapper;

import static com.farmer.be.domain.AdmissionAsserts.*;
import static com.farmer.be.domain.AdmissionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdmissionMapperTest {

    private AdmissionMapper admissionMapper;

    @BeforeEach
    void setUp() {
        admissionMapper = new AdmissionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdmissionSample1();
        var actual = admissionMapper.toEntity(admissionMapper.toDto(expected));
        assertAdmissionAllPropertiesEquals(expected, actual);
    }
}
