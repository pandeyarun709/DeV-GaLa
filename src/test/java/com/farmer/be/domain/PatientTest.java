package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestReportTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTestSamples.*;
import static com.farmer.be.domain.PatientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PatientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patient.class);
        Patient patient1 = getPatientSample1();
        Patient patient2 = new Patient();
        assertThat(patient1).isNotEqualTo(patient2);

        patient2.setId(patient1.getId());
        assertThat(patient1).isEqualTo(patient2);

        patient2 = getPatientSample2();
        assertThat(patient1).isNotEqualTo(patient2);
    }

    @Test
    void addressTest() {
        Patient patient = getPatientRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        patient.setAddress(addressBack);
        assertThat(patient.getAddress()).isEqualTo(addressBack);

        patient.address(null);
        assertThat(patient.getAddress()).isNull();
    }

    @Test
    void diagnosticTestReportTest() {
        Patient patient = getPatientRandomSampleGenerator();
        DiagnosticTestReport diagnosticTestReportBack = getDiagnosticTestReportRandomSampleGenerator();

        patient.addDiagnosticTestReport(diagnosticTestReportBack);
        assertThat(patient.getDiagnosticTestReports()).containsOnly(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getPatient()).isEqualTo(patient);

        patient.removeDiagnosticTestReport(diagnosticTestReportBack);
        assertThat(patient.getDiagnosticTestReports()).doesNotContain(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getPatient()).isNull();

        patient.diagnosticTestReports(new HashSet<>(Set.of(diagnosticTestReportBack)));
        assertThat(patient.getDiagnosticTestReports()).containsOnly(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getPatient()).isEqualTo(patient);

        patient.setDiagnosticTestReports(new HashSet<>());
        assertThat(patient.getDiagnosticTestReports()).doesNotContain(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getPatient()).isNull();
    }

    @Test
    void doctorVisitTest() {
        Patient patient = getPatientRandomSampleGenerator();
        DoctorVisit doctorVisitBack = getDoctorVisitRandomSampleGenerator();

        patient.addDoctorVisit(doctorVisitBack);
        assertThat(patient.getDoctorVisits()).containsOnly(doctorVisitBack);
        assertThat(doctorVisitBack.getPatient()).isEqualTo(patient);

        patient.removeDoctorVisit(doctorVisitBack);
        assertThat(patient.getDoctorVisits()).doesNotContain(doctorVisitBack);
        assertThat(doctorVisitBack.getPatient()).isNull();

        patient.doctorVisits(new HashSet<>(Set.of(doctorVisitBack)));
        assertThat(patient.getDoctorVisits()).containsOnly(doctorVisitBack);
        assertThat(doctorVisitBack.getPatient()).isEqualTo(patient);

        patient.setDoctorVisits(new HashSet<>());
        assertThat(patient.getDoctorVisits()).doesNotContain(doctorVisitBack);
        assertThat(doctorVisitBack.getPatient()).isNull();
    }

    @Test
    void admissionTest() {
        Patient patient = getPatientRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        patient.addAdmission(admissionBack);
        assertThat(patient.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getPatient()).isEqualTo(patient);

        patient.removeAdmission(admissionBack);
        assertThat(patient.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getPatient()).isNull();

        patient.admissions(new HashSet<>(Set.of(admissionBack)));
        assertThat(patient.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getPatient()).isEqualTo(patient);

        patient.setAdmissions(new HashSet<>());
        assertThat(patient.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getPatient()).isNull();
    }
}
