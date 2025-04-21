package com.farmer.be.service.mapper;

import static com.farmer.be.domain.MedicineDoseAsserts.*;
import static com.farmer.be.domain.MedicineDoseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicineDoseMapperTest {

    private MedicineDoseMapper medicineDoseMapper;

    @BeforeEach
    void setUp() {
        medicineDoseMapper = new MedicineDoseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMedicineDoseSample1();
        var actual = medicineDoseMapper.toEntity(medicineDoseMapper.toDto(expected));
        assertMedicineDoseAllPropertiesEquals(expected, actual);
    }
}
