package com.farmer.be.domain;

import static com.farmer.be.domain.MedicineDoseTestSamples.*;
import static com.farmer.be.domain.MedicineTestSamples.*;
import static com.farmer.be.domain.PrescriptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicineDoseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineDose.class);
        MedicineDose medicineDose1 = getMedicineDoseSample1();
        MedicineDose medicineDose2 = new MedicineDose();
        assertThat(medicineDose1).isNotEqualTo(medicineDose2);

        medicineDose2.setId(medicineDose1.getId());
        assertThat(medicineDose1).isEqualTo(medicineDose2);

        medicineDose2 = getMedicineDoseSample2();
        assertThat(medicineDose1).isNotEqualTo(medicineDose2);
    }

    @Test
    void prescriptionTest() {
        MedicineDose medicineDose = getMedicineDoseRandomSampleGenerator();
        Prescription prescriptionBack = getPrescriptionRandomSampleGenerator();

        medicineDose.setPrescription(prescriptionBack);
        assertThat(medicineDose.getPrescription()).isEqualTo(prescriptionBack);

        medicineDose.prescription(null);
        assertThat(medicineDose.getPrescription()).isNull();
    }

    @Test
    void medicineTest() {
        MedicineDose medicineDose = getMedicineDoseRandomSampleGenerator();
        Medicine medicineBack = getMedicineRandomSampleGenerator();

        medicineDose.setMedicine(medicineBack);
        assertThat(medicineDose.getMedicine()).isEqualTo(medicineBack);

        medicineDose.medicine(null);
        assertThat(medicineDose.getMedicine()).isNull();
    }
}
