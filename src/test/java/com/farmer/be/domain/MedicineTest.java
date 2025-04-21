package com.farmer.be.domain;

import static com.farmer.be.domain.MedicineBatchTestSamples.*;
import static com.farmer.be.domain.MedicineDoseTestSamples.*;
import static com.farmer.be.domain.MedicineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MedicineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicine.class);
        Medicine medicine1 = getMedicineSample1();
        Medicine medicine2 = new Medicine();
        assertThat(medicine1).isNotEqualTo(medicine2);

        medicine2.setId(medicine1.getId());
        assertThat(medicine1).isEqualTo(medicine2);

        medicine2 = getMedicineSample2();
        assertThat(medicine1).isNotEqualTo(medicine2);
    }

    @Test
    void medicineDoseTest() {
        Medicine medicine = getMedicineRandomSampleGenerator();
        MedicineDose medicineDoseBack = getMedicineDoseRandomSampleGenerator();

        medicine.addMedicineDose(medicineDoseBack);
        assertThat(medicine.getMedicineDoses()).containsOnly(medicineDoseBack);
        assertThat(medicineDoseBack.getMedicine()).isEqualTo(medicine);

        medicine.removeMedicineDose(medicineDoseBack);
        assertThat(medicine.getMedicineDoses()).doesNotContain(medicineDoseBack);
        assertThat(medicineDoseBack.getMedicine()).isNull();

        medicine.medicineDoses(new HashSet<>(Set.of(medicineDoseBack)));
        assertThat(medicine.getMedicineDoses()).containsOnly(medicineDoseBack);
        assertThat(medicineDoseBack.getMedicine()).isEqualTo(medicine);

        medicine.setMedicineDoses(new HashSet<>());
        assertThat(medicine.getMedicineDoses()).doesNotContain(medicineDoseBack);
        assertThat(medicineDoseBack.getMedicine()).isNull();
    }

    @Test
    void medicineBatchTest() {
        Medicine medicine = getMedicineRandomSampleGenerator();
        MedicineBatch medicineBatchBack = getMedicineBatchRandomSampleGenerator();

        medicine.addMedicineBatch(medicineBatchBack);
        assertThat(medicine.getMedicineBatches()).containsOnly(medicineBatchBack);
        assertThat(medicineBatchBack.getMed()).isEqualTo(medicine);

        medicine.removeMedicineBatch(medicineBatchBack);
        assertThat(medicine.getMedicineBatches()).doesNotContain(medicineBatchBack);
        assertThat(medicineBatchBack.getMed()).isNull();

        medicine.medicineBatches(new HashSet<>(Set.of(medicineBatchBack)));
        assertThat(medicine.getMedicineBatches()).containsOnly(medicineBatchBack);
        assertThat(medicineBatchBack.getMed()).isEqualTo(medicine);

        medicine.setMedicineBatches(new HashSet<>());
        assertThat(medicine.getMedicineBatches()).doesNotContain(medicineBatchBack);
        assertThat(medicineBatchBack.getMed()).isNull();
    }
}
