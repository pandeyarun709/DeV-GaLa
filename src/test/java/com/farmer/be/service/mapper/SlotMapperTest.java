package com.farmer.be.service.mapper;

import static com.farmer.be.domain.SlotAsserts.*;
import static com.farmer.be.domain.SlotTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SlotMapperTest {

    private SlotMapper slotMapper;

    @BeforeEach
    void setUp() {
        slotMapper = new SlotMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSlotSample1();
        var actual = slotMapper.toEntity(slotMapper.toDto(expected));
        assertSlotAllPropertiesEquals(expected, actual);
    }
}
