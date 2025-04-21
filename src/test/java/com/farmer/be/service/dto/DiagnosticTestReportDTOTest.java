package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiagnosticTestReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosticTestReportDTO.class);
        DiagnosticTestReportDTO diagnosticTestReportDTO1 = new DiagnosticTestReportDTO();
        diagnosticTestReportDTO1.setId(1L);
        DiagnosticTestReportDTO diagnosticTestReportDTO2 = new DiagnosticTestReportDTO();
        assertThat(diagnosticTestReportDTO1).isNotEqualTo(diagnosticTestReportDTO2);
        diagnosticTestReportDTO2.setId(diagnosticTestReportDTO1.getId());
        assertThat(diagnosticTestReportDTO1).isEqualTo(diagnosticTestReportDTO2);
        diagnosticTestReportDTO2.setId(2L);
        assertThat(diagnosticTestReportDTO1).isNotEqualTo(diagnosticTestReportDTO2);
        diagnosticTestReportDTO1.setId(null);
        assertThat(diagnosticTestReportDTO1).isNotEqualTo(diagnosticTestReportDTO2);
    }
}
