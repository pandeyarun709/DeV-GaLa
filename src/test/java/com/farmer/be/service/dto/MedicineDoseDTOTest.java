package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicineDoseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineDoseDTO.class);
        MedicineDoseDTO medicineDoseDTO1 = new MedicineDoseDTO();
        medicineDoseDTO1.setId(1L);
        MedicineDoseDTO medicineDoseDTO2 = new MedicineDoseDTO();
        assertThat(medicineDoseDTO1).isNotEqualTo(medicineDoseDTO2);
        medicineDoseDTO2.setId(medicineDoseDTO1.getId());
        assertThat(medicineDoseDTO1).isEqualTo(medicineDoseDTO2);
        medicineDoseDTO2.setId(2L);
        assertThat(medicineDoseDTO1).isNotEqualTo(medicineDoseDTO2);
        medicineDoseDTO1.setId(null);
        assertThat(medicineDoseDTO1).isNotEqualTo(medicineDoseDTO2);
    }
}
