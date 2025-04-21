package com.farmer.be.service.mapper;

import static com.farmer.be.domain.DoctorVisitAsserts.*;
import static com.farmer.be.domain.DoctorVisitTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorVisitMapperTest {

    private DoctorVisitMapper doctorVisitMapper;

    @BeforeEach
    void setUp() {
        doctorVisitMapper = new DoctorVisitMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDoctorVisitSample1();
        var actual = doctorVisitMapper.toEntity(doctorVisitMapper.toDto(expected));
        assertDoctorVisitAllPropertiesEquals(expected, actual);
    }
}
