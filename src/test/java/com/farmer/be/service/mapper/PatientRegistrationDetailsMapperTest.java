package com.farmer.be.service.mapper;

import static com.farmer.be.domain.PatientRegistrationDetailsAsserts.*;
import static com.farmer.be.domain.PatientRegistrationDetailsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientRegistrationDetailsMapperTest {

    private PatientRegistrationDetailsMapper patientRegistrationDetailsMapper;

    @BeforeEach
    void setUp() {
        patientRegistrationDetailsMapper = new PatientRegistrationDetailsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPatientRegistrationDetailsSample1();
        var actual = patientRegistrationDetailsMapper.toEntity(patientRegistrationDetailsMapper.toDto(expected));
        assertPatientRegistrationDetailsAllPropertiesEquals(expected, actual);
    }
}
