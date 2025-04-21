package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorVisitTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorVisitTypeDTO.class);
        DoctorVisitTypeDTO doctorVisitTypeDTO1 = new DoctorVisitTypeDTO();
        doctorVisitTypeDTO1.setId(1L);
        DoctorVisitTypeDTO doctorVisitTypeDTO2 = new DoctorVisitTypeDTO();
        assertThat(doctorVisitTypeDTO1).isNotEqualTo(doctorVisitTypeDTO2);
        doctorVisitTypeDTO2.setId(doctorVisitTypeDTO1.getId());
        assertThat(doctorVisitTypeDTO1).isEqualTo(doctorVisitTypeDTO2);
        doctorVisitTypeDTO2.setId(2L);
        assertThat(doctorVisitTypeDTO1).isNotEqualTo(doctorVisitTypeDTO2);
        doctorVisitTypeDTO1.setId(null);
        assertThat(doctorVisitTypeDTO1).isNotEqualTo(doctorVisitTypeDTO2);
    }
}
