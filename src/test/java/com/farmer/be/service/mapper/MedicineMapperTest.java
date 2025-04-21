package com.farmer.be.service.mapper;

import static com.farmer.be.domain.MedicineAsserts.*;
import static com.farmer.be.domain.MedicineTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicineMapperTest {

    private MedicineMapper medicineMapper;

    @BeforeEach
    void setUp() {
        medicineMapper = new MedicineMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMedicineSample1();
        var actual = medicineMapper.toEntity(medicineMapper.toDto(expected));
        assertMedicineAllPropertiesEquals(expected, actual);
    }
}
