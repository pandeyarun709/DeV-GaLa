package com.farmer.be.service.mapper;

import static com.farmer.be.domain.LedgerItemAsserts.*;
import static com.farmer.be.domain.LedgerItemTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LedgerItemMapperTest {

    private LedgerItemMapper ledgerItemMapper;

    @BeforeEach
    void setUp() {
        ledgerItemMapper = new LedgerItemMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLedgerItemSample1();
        var actual = ledgerItemMapper.toEntity(ledgerItemMapper.toDto(expected));
        assertLedgerItemAllPropertiesEquals(expected, actual);
    }
}
