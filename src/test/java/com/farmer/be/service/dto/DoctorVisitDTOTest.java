package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorVisitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorVisitDTO.class);
        DoctorVisitDTO doctorVisitDTO1 = new DoctorVisitDTO();
        doctorVisitDTO1.setId(1L);
        DoctorVisitDTO doctorVisitDTO2 = new DoctorVisitDTO();
        assertThat(doctorVisitDTO1).isNotEqualTo(doctorVisitDTO2);
        doctorVisitDTO2.setId(doctorVisitDTO1.getId());
        assertThat(doctorVisitDTO1).isEqualTo(doctorVisitDTO2);
        doctorVisitDTO2.setId(2L);
        assertThat(doctorVisitDTO1).isNotEqualTo(doctorVisitDTO2);
        doctorVisitDTO1.setId(null);
        assertThat(doctorVisitDTO1).isNotEqualTo(doctorVisitDTO2);
    }
}
