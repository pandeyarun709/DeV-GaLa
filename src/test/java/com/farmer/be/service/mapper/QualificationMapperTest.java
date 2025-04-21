package com.farmer.be.service.mapper;

import static com.farmer.be.domain.QualificationAsserts.*;
import static com.farmer.be.domain.QualificationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QualificationMapperTest {

    private QualificationMapper qualificationMapper;

    @BeforeEach
    void setUp() {
        qualificationMapper = new QualificationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getQualificationSample1();
        var actual = qualificationMapper.toEntity(qualificationMapper.toDto(expected));
        assertQualificationAllPropertiesEquals(expected, actual);
    }
}
