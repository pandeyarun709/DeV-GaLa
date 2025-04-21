package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicineBatchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineBatchDTO.class);
        MedicineBatchDTO medicineBatchDTO1 = new MedicineBatchDTO();
        medicineBatchDTO1.setId(1L);
        MedicineBatchDTO medicineBatchDTO2 = new MedicineBatchDTO();
        assertThat(medicineBatchDTO1).isNotEqualTo(medicineBatchDTO2);
        medicineBatchDTO2.setId(medicineBatchDTO1.getId());
        assertThat(medicineBatchDTO1).isEqualTo(medicineBatchDTO2);
        medicineBatchDTO2.setId(2L);
        assertThat(medicineBatchDTO1).isNotEqualTo(medicineBatchDTO2);
        medicineBatchDTO1.setId(null);
        assertThat(medicineBatchDTO1).isNotEqualTo(medicineBatchDTO2);
    }
}
