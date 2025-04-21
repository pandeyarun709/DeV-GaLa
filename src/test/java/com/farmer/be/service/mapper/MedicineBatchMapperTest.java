package com.farmer.be.service.mapper;

import static com.farmer.be.domain.MedicineBatchAsserts.*;
import static com.farmer.be.domain.MedicineBatchTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicineBatchMapperTest {

    private MedicineBatchMapper medicineBatchMapper;

    @BeforeEach
    void setUp() {
        medicineBatchMapper = new MedicineBatchMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMedicineBatchSample1();
        var actual = medicineBatchMapper.toEntity(medicineBatchMapper.toDto(expected));
        assertMedicineBatchAllPropertiesEquals(expected, actual);
    }
}
