package com.farmer.be.domain;

import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.BedTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestReportTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTestSamples.*;
import static com.farmer.be.domain.EmployeeTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static com.farmer.be.domain.LedgerItemTestSamples.*;
import static com.farmer.be.domain.MedicineBatchTestSamples.*;
import static com.farmer.be.domain.PatientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdmissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Admission.class);
        Admission admission1 = getAdmissionSample1();
        Admission admission2 = new Admission();
        assertThat(admission1).isNotEqualTo(admission2);

        admission2.setId(admission1.getId());
        assertThat(admission1).isEqualTo(admission2);

        admission2 = getAdmissionSample2();
        assertThat(admission1).isNotEqualTo(admission2);
    }

    @Test
    void medicineBatchTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        MedicineBatch medicineBatchBack = getMedicineBatchRandomSampleGenerator();

        admission.addMedicineBatch(medicineBatchBack);
        assertThat(admission.getMedicineBatches()).containsOnly(medicineBatchBack);
        assertThat(medicineBatchBack.getAdmissions()).isEqualTo(admission);

        admission.removeMedicineBatch(medicineBatchBack);
        assertThat(admission.getMedicineBatches()).doesNotContain(medicineBatchBack);
        assertThat(medicineBatchBack.getAdmissions()).isNull();

        admission.medicineBatches(new HashSet<>(Set.of(medicineBatchBack)));
        assertThat(admission.getMedicineBatches()).containsOnly(medicineBatchBack);
        assertThat(medicineBatchBack.getAdmissions()).isEqualTo(admission);

        admission.setMedicineBatches(new HashSet<>());
        assertThat(admission.getMedicineBatches()).doesNotContain(medicineBatchBack);
        assertThat(medicineBatchBack.getAdmissions()).isNull();
    }

    @Test
    void diagnosticTestReportTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        DiagnosticTestReport diagnosticTestReportBack = getDiagnosticTestReportRandomSampleGenerator();

        admission.addDiagnosticTestReport(diagnosticTestReportBack);
        assertThat(admission.getDiagnosticTestReports()).containsOnly(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getAdmissions()).isEqualTo(admission);

        admission.removeDiagnosticTestReport(diagnosticTestReportBack);
        assertThat(admission.getDiagnosticTestReports()).doesNotContain(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getAdmissions()).isNull();

        admission.diagnosticTestReports(new HashSet<>(Set.of(diagnosticTestReportBack)));
        assertThat(admission.getDiagnosticTestReports()).containsOnly(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getAdmissions()).isEqualTo(admission);

        admission.setDiagnosticTestReports(new HashSet<>());
        assertThat(admission.getDiagnosticTestReports()).doesNotContain(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getAdmissions()).isNull();
    }

    @Test
    void doctorVisitTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        DoctorVisit doctorVisitBack = getDoctorVisitRandomSampleGenerator();

        admission.addDoctorVisit(doctorVisitBack);
        assertThat(admission.getDoctorVisits()).containsOnly(doctorVisitBack);
        assertThat(doctorVisitBack.getAdmissions()).isEqualTo(admission);

        admission.removeDoctorVisit(doctorVisitBack);
        assertThat(admission.getDoctorVisits()).doesNotContain(doctorVisitBack);
        assertThat(doctorVisitBack.getAdmissions()).isNull();

        admission.doctorVisits(new HashSet<>(Set.of(doctorVisitBack)));
        assertThat(admission.getDoctorVisits()).containsOnly(doctorVisitBack);
        assertThat(doctorVisitBack.getAdmissions()).isEqualTo(admission);

        admission.setDoctorVisits(new HashSet<>());
        assertThat(admission.getDoctorVisits()).doesNotContain(doctorVisitBack);
        assertThat(doctorVisitBack.getAdmissions()).isNull();
    }

    @Test
    void ledgerItemTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        LedgerItem ledgerItemBack = getLedgerItemRandomSampleGenerator();

        admission.addLedgerItem(ledgerItemBack);
        assertThat(admission.getLedgerItems()).containsOnly(ledgerItemBack);
        assertThat(ledgerItemBack.getAdmission()).isEqualTo(admission);

        admission.removeLedgerItem(ledgerItemBack);
        assertThat(admission.getLedgerItems()).doesNotContain(ledgerItemBack);
        assertThat(ledgerItemBack.getAdmission()).isNull();

        admission.ledgerItems(new HashSet<>(Set.of(ledgerItemBack)));
        assertThat(admission.getLedgerItems()).containsOnly(ledgerItemBack);
        assertThat(ledgerItemBack.getAdmission()).isEqualTo(admission);

        admission.setLedgerItems(new HashSet<>());
        assertThat(admission.getLedgerItems()).doesNotContain(ledgerItemBack);
        assertThat(ledgerItemBack.getAdmission()).isNull();
    }

    @Test
    void bedsTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        Bed bedBack = getBedRandomSampleGenerator();

        admission.addBeds(bedBack);
        assertThat(admission.getBeds()).containsOnly(bedBack);

        admission.removeBeds(bedBack);
        assertThat(admission.getBeds()).doesNotContain(bedBack);

        admission.beds(new HashSet<>(Set.of(bedBack)));
        assertThat(admission.getBeds()).containsOnly(bedBack);

        admission.setBeds(new HashSet<>());
        assertThat(admission.getBeds()).doesNotContain(bedBack);
    }

    @Test
    void patientTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        Patient patientBack = getPatientRandomSampleGenerator();

        admission.setPatient(patientBack);
        assertThat(admission.getPatient()).isEqualTo(patientBack);

        admission.patient(null);
        assertThat(admission.getPatient()).isNull();
    }

    @Test
    void hospitalTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        Hospital hospitalBack = getHospitalRandomSampleGenerator();

        admission.setHospital(hospitalBack);
        assertThat(admission.getHospital()).isEqualTo(hospitalBack);

        admission.hospital(null);
        assertThat(admission.getHospital()).isNull();
    }

    @Test
    void admittedUnderTest() {
        Admission admission = getAdmissionRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        admission.setAdmittedUnder(employeeBack);
        assertThat(admission.getAdmittedUnder()).isEqualTo(employeeBack);

        admission.admittedUnder(null);
        assertThat(admission.getAdmittedUnder()).isNull();
    }
}
