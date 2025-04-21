package com.farmer.be.service.mapper;

import static com.farmer.be.domain.ReferralDoctorAsserts.*;
import static com.farmer.be.domain.ReferralDoctorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReferralDoctorMapperTest {

    private ReferralDoctorMapper referralDoctorMapper;

    @BeforeEach
    void setUp() {
        referralDoctorMapper = new ReferralDoctorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getReferralDoctorSample1();
        var actual = referralDoctorMapper.toEntity(referralDoctorMapper.toDto(expected));
        assertReferralDoctorAllPropertiesEquals(expected, actual);
    }
}
