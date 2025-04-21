package com.farmer.be.domain;

import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestReportTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestTestSamples.*;
import static com.farmer.be.domain.PatientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiagnosticTestReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosticTestReport.class);
        DiagnosticTestReport diagnosticTestReport1 = getDiagnosticTestReportSample1();
        DiagnosticTestReport diagnosticTestReport2 = new DiagnosticTestReport();
        assertThat(diagnosticTestReport1).isNotEqualTo(diagnosticTestReport2);

        diagnosticTestReport2.setId(diagnosticTestReport1.getId());
        assertThat(diagnosticTestReport1).isEqualTo(diagnosticTestReport2);

        diagnosticTestReport2 = getDiagnosticTestReportSample2();
        assertThat(diagnosticTestReport1).isNotEqualTo(diagnosticTestReport2);
    }

    @Test
    void patientTest() {
        DiagnosticTestReport diagnosticTestReport = getDiagnosticTestReportRandomSampleGenerator();
        Patient patientBack = getPatientRandomSampleGenerator();

        diagnosticTestReport.setPatient(patientBack);
        assertThat(diagnosticTestReport.getPatient()).isEqualTo(patientBack);

        diagnosticTestReport.patient(null);
        assertThat(diagnosticTestReport.getPatient()).isNull();
    }

    @Test
    void testTest() {
        DiagnosticTestReport diagnosticTestReport = getDiagnosticTestReportRandomSampleGenerator();
        DiagnosticTest diagnosticTestBack = getDiagnosticTestRandomSampleGenerator();

        diagnosticTestReport.setTest(diagnosticTestBack);
        assertThat(diagnosticTestReport.getTest()).isEqualTo(diagnosticTestBack);

        diagnosticTestReport.test(null);
        assertThat(diagnosticTestReport.getTest()).isNull();
    }

    @Test
    void admissionsTest() {
        DiagnosticTestReport diagnosticTestReport = getDiagnosticTestReportRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        diagnosticTestReport.setAdmissions(admissionBack);
        assertThat(diagnosticTestReport.getAdmissions()).isEqualTo(admissionBack);

        diagnosticTestReport.admissions(null);
        assertThat(diagnosticTestReport.getAdmissions()).isNull();
    }
}
