package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QualificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QualificationDTO.class);
        QualificationDTO qualificationDTO1 = new QualificationDTO();
        qualificationDTO1.setId(1L);
        QualificationDTO qualificationDTO2 = new QualificationDTO();
        assertThat(qualificationDTO1).isNotEqualTo(qualificationDTO2);
        qualificationDTO2.setId(qualificationDTO1.getId());
        assertThat(qualificationDTO1).isEqualTo(qualificationDTO2);
        qualificationDTO2.setId(2L);
        assertThat(qualificationDTO1).isNotEqualTo(qualificationDTO2);
        qualificationDTO1.setId(null);
        assertThat(qualificationDTO1).isNotEqualTo(qualificationDTO2);
    }
}
