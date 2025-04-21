package com.farmer.be.domain;

import static com.farmer.be.domain.DiagnosticTestTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTestSamples.*;
import static com.farmer.be.domain.MedicineDoseTestSamples.*;
import static com.farmer.be.domain.PrescriptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PrescriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prescription.class);
        Prescription prescription1 = getPrescriptionSample1();
        Prescription prescription2 = new Prescription();
        assertThat(prescription1).isNotEqualTo(prescription2);

        prescription2.setId(prescription1.getId());
        assertThat(prescription1).isEqualTo(prescription2);

        prescription2 = getPrescriptionSample2();
        assertThat(prescription1).isNotEqualTo(prescription2);
    }

    @Test
    void medicineDoseTest() {
        Prescription prescription = getPrescriptionRandomSampleGenerator();
        MedicineDose medicineDoseBack = getMedicineDoseRandomSampleGenerator();

        prescription.addMedicineDose(medicineDoseBack);
        assertThat(prescription.getMedicineDoses()).containsOnly(medicineDoseBack);
        assertThat(medicineDoseBack.getPrescription()).isEqualTo(prescription);

        prescription.removeMedicineDose(medicineDoseBack);
        assertThat(prescription.getMedicineDoses()).doesNotContain(medicineDoseBack);
        assertThat(medicineDoseBack.getPrescription()).isNull();

        prescription.medicineDoses(new HashSet<>(Set.of(medicineDoseBack)));
        assertThat(prescription.getMedicineDoses()).containsOnly(medicineDoseBack);
        assertThat(medicineDoseBack.getPrescription()).isEqualTo(prescription);

        prescription.setMedicineDoses(new HashSet<>());
        assertThat(prescription.getMedicineDoses()).doesNotContain(medicineDoseBack);
        assertThat(medicineDoseBack.getPrescription()).isNull();
    }

    @Test
    void diagnosticTestTest() {
        Prescription prescription = getPrescriptionRandomSampleGenerator();
        DiagnosticTest diagnosticTestBack = getDiagnosticTestRandomSampleGenerator();

        prescription.addDiagnosticTest(diagnosticTestBack);
        assertThat(prescription.getDiagnosticTests()).containsOnly(diagnosticTestBack);
        assertThat(diagnosticTestBack.getPrescription()).isEqualTo(prescription);

        prescription.removeDiagnosticTest(diagnosticTestBack);
        assertThat(prescription.getDiagnosticTests()).doesNotContain(diagnosticTestBack);
        assertThat(diagnosticTestBack.getPrescription()).isNull();

        prescription.diagnosticTests(new HashSet<>(Set.of(diagnosticTestBack)));
        assertThat(prescription.getDiagnosticTests()).containsOnly(diagnosticTestBack);
        assertThat(diagnosticTestBack.getPrescription()).isEqualTo(prescription);

        prescription.setDiagnosticTests(new HashSet<>());
        assertThat(prescription.getDiagnosticTests()).doesNotContain(diagnosticTestBack);
        assertThat(diagnosticTestBack.getPrescription()).isNull();
    }

    @Test
    void doctorVisitTypeTest() {
        Prescription prescription = getPrescriptionRandomSampleGenerator();
        DoctorVisit doctorVisitBack = getDoctorVisitRandomSampleGenerator();

        prescription.setDoctorVisitType(doctorVisitBack);
        assertThat(prescription.getDoctorVisitType()).isEqualTo(doctorVisitBack);
        assertThat(doctorVisitBack.getPrescription()).isEqualTo(prescription);

        prescription.doctorVisitType(null);
        assertThat(prescription.getDoctorVisitType()).isNull();
        assertThat(doctorVisitBack.getPrescription()).isNull();
    }
}
