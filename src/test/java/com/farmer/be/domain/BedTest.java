package com.farmer.be.domain;

import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.BedTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bed.class);
        Bed bed1 = getBedSample1();
        Bed bed2 = new Bed();
        assertThat(bed1).isNotEqualTo(bed2);

        bed2.setId(bed1.getId());
        assertThat(bed1).isEqualTo(bed2);

        bed2 = getBedSample2();
        assertThat(bed1).isNotEqualTo(bed2);
    }

    @Test
    void hospitalTest() {
        Bed bed = getBedRandomSampleGenerator();
        Hospital hospitalBack = getHospitalRandomSampleGenerator();

        bed.setHospital(hospitalBack);
        assertThat(bed.getHospital()).isEqualTo(hospitalBack);

        bed.hospital(null);
        assertThat(bed.getHospital()).isNull();
    }

    @Test
    void admissionsTest() {
        Bed bed = getBedRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        bed.addAdmissions(admissionBack);
        assertThat(bed.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getBeds()).containsOnly(bed);

        bed.removeAdmissions(admissionBack);
        assertThat(bed.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getBeds()).doesNotContain(bed);

        bed.admissions(new HashSet<>(Set.of(admissionBack)));
        assertThat(bed.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getBeds()).containsOnly(bed);

        bed.setAdmissions(new HashSet<>());
        assertThat(bed.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getBeds()).doesNotContain(bed);
    }
}
