package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReferralDoctorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralDoctorDTO.class);
        ReferralDoctorDTO referralDoctorDTO1 = new ReferralDoctorDTO();
        referralDoctorDTO1.setId(1L);
        ReferralDoctorDTO referralDoctorDTO2 = new ReferralDoctorDTO();
        assertThat(referralDoctorDTO1).isNotEqualTo(referralDoctorDTO2);
        referralDoctorDTO2.setId(referralDoctorDTO1.getId());
        assertThat(referralDoctorDTO1).isEqualTo(referralDoctorDTO2);
        referralDoctorDTO2.setId(2L);
        assertThat(referralDoctorDTO1).isNotEqualTo(referralDoctorDTO2);
        referralDoctorDTO1.setId(null);
        assertThat(referralDoctorDTO1).isNotEqualTo(referralDoctorDTO2);
    }
}
