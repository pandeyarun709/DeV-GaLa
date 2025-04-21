package com.farmer.be.domain;

import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static com.farmer.be.domain.MedicineBatchTestSamples.*;
import static com.farmer.be.domain.MedicineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicineBatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineBatch.class);
        MedicineBatch medicineBatch1 = getMedicineBatchSample1();
        MedicineBatch medicineBatch2 = new MedicineBatch();
        assertThat(medicineBatch1).isNotEqualTo(medicineBatch2);

        medicineBatch2.setId(medicineBatch1.getId());
        assertThat(medicineBatch1).isEqualTo(medicineBatch2);

        medicineBatch2 = getMedicineBatchSample2();
        assertThat(medicineBatch1).isNotEqualTo(medicineBatch2);
    }

    @Test
    void medTest() {
        MedicineBatch medicineBatch = getMedicineBatchRandomSampleGenerator();
        Medicine medicineBack = getMedicineRandomSampleGenerator();

        medicineBatch.setMed(medicineBack);
        assertThat(medicineBatch.getMed()).isEqualTo(medicineBack);

        medicineBatch.med(null);
        assertThat(medicineBatch.getMed()).isNull();
    }

    @Test
    void hospitalTest() {
        MedicineBatch medicineBatch = getMedicineBatchRandomSampleGenerator();
        Hospital hospitalBack = getHospitalRandomSampleGenerator();

        medicineBatch.setHospital(hospitalBack);
        assertThat(medicineBatch.getHospital()).isEqualTo(hospitalBack);

        medicineBatch.hospital(null);
        assertThat(medicineBatch.getHospital()).isNull();
    }

    @Test
    void admissionsTest() {
        MedicineBatch medicineBatch = getMedicineBatchRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        medicineBatch.setAdmissions(admissionBack);
        assertThat(medicineBatch.getAdmissions()).isEqualTo(admissionBack);

        medicineBatch.admissions(null);
        assertThat(medicineBatch.getAdmissions()).isNull();
    }
}
