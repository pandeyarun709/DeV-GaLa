package com.farmer.be.service.mapper;

import static com.farmer.be.domain.DoctorVisitTypeAsserts.*;
import static com.farmer.be.domain.DoctorVisitTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorVisitTypeMapperTest {

    private DoctorVisitTypeMapper doctorVisitTypeMapper;

    @BeforeEach
    void setUp() {
        doctorVisitTypeMapper = new DoctorVisitTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDoctorVisitTypeSample1();
        var actual = doctorVisitTypeMapper.toEntity(doctorVisitTypeMapper.toDto(expected));
        assertDoctorVisitTypeAllPropertiesEquals(expected, actual);
    }
}
