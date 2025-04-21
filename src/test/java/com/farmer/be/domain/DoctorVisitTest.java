package com.farmer.be.domain;

import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTypeTestSamples.*;
import static com.farmer.be.domain.PatientTestSamples.*;
import static com.farmer.be.domain.PrescriptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoctorVisitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorVisit.class);
        DoctorVisit doctorVisit1 = getDoctorVisitSample1();
        DoctorVisit doctorVisit2 = new DoctorVisit();
        assertThat(doctorVisit1).isNotEqualTo(doctorVisit2);

        doctorVisit2.setId(doctorVisit1.getId());
        assertThat(doctorVisit1).isEqualTo(doctorVisit2);

        doctorVisit2 = getDoctorVisitSample2();
        assertThat(doctorVisit1).isNotEqualTo(doctorVisit2);
    }

    @Test
    void prescriptionTest() {
        DoctorVisit doctorVisit = getDoctorVisitRandomSampleGenerator();
        Prescription prescriptionBack = getPrescriptionRandomSampleGenerator();

        doctorVisit.setPrescription(prescriptionBack);
        assertThat(doctorVisit.getPrescription()).isEqualTo(prescriptionBack);

        doctorVisit.prescription(null);
        assertThat(doctorVisit.getPrescription()).isNull();
    }

    @Test
    void doctorVisitTypeTest() {
        DoctorVisit doctorVisit = getDoctorVisitRandomSampleGenerator();
        DoctorVisitType doctorVisitTypeBack = getDoctorVisitTypeRandomSampleGenerator();

        doctorVisit.setDoctorVisitType(doctorVisitTypeBack);
        assertThat(doctorVisit.getDoctorVisitType()).isEqualTo(doctorVisitTypeBack);

        doctorVisit.doctorVisitType(null);
        assertThat(doctorVisit.getDoctorVisitType()).isNull();
    }

    @Test
    void patientTest() {
        DoctorVisit doctorVisit = getDoctorVisitRandomSampleGenerator();
        Patient patientBack = getPatientRandomSampleGenerator();

        doctorVisit.setPatient(patientBack);
        assertThat(doctorVisit.getPatient()).isEqualTo(patientBack);

        doctorVisit.patient(null);
        assertThat(doctorVisit.getPatient()).isNull();
    }

    @Test
    void admissionsTest() {
        DoctorVisit doctorVisit = getDoctorVisitRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        doctorVisit.setAdmissions(admissionBack);
        assertThat(doctorVisit.getAdmissions()).isEqualTo(admissionBack);

        doctorVisit.admissions(null);
        assertThat(doctorVisit.getAdmissions()).isNull();
    }
}
