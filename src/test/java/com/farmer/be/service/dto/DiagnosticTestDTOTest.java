package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiagnosticTestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosticTestDTO.class);
        DiagnosticTestDTO diagnosticTestDTO1 = new DiagnosticTestDTO();
        diagnosticTestDTO1.setId(1L);
        DiagnosticTestDTO diagnosticTestDTO2 = new DiagnosticTestDTO();
        assertThat(diagnosticTestDTO1).isNotEqualTo(diagnosticTestDTO2);
        diagnosticTestDTO2.setId(diagnosticTestDTO1.getId());
        assertThat(diagnosticTestDTO1).isEqualTo(diagnosticTestDTO2);
        diagnosticTestDTO2.setId(2L);
        assertThat(diagnosticTestDTO1).isNotEqualTo(diagnosticTestDTO2);
        diagnosticTestDTO1.setId(null);
        assertThat(diagnosticTestDTO1).isNotEqualTo(diagnosticTestDTO2);
    }
}
