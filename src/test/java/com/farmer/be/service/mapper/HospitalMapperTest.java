package com.farmer.be.service.mapper;

import static com.farmer.be.domain.HospitalAsserts.*;
import static com.farmer.be.domain.HospitalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HospitalMapperTest {

    private HospitalMapper hospitalMapper;

    @BeforeEach
    void setUp() {
        hospitalMapper = new HospitalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHospitalSample1();
        var actual = hospitalMapper.toEntity(hospitalMapper.toDto(expected));
        assertHospitalAllPropertiesEquals(expected, actual);
    }
}
