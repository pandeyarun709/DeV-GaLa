package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientRegistrationDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientRegistrationDetailsDTO.class);
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO1 = new PatientRegistrationDetailsDTO();
        patientRegistrationDetailsDTO1.setId(1L);
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO2 = new PatientRegistrationDetailsDTO();
        assertThat(patientRegistrationDetailsDTO1).isNotEqualTo(patientRegistrationDetailsDTO2);
        patientRegistrationDetailsDTO2.setId(patientRegistrationDetailsDTO1.getId());
        assertThat(patientRegistrationDetailsDTO1).isEqualTo(patientRegistrationDetailsDTO2);
        patientRegistrationDetailsDTO2.setId(2L);
        assertThat(patientRegistrationDetailsDTO1).isNotEqualTo(patientRegistrationDetailsDTO2);
        patientRegistrationDetailsDTO1.setId(null);
        assertThat(patientRegistrationDetailsDTO1).isNotEqualTo(patientRegistrationDetailsDTO2);
    }
}
