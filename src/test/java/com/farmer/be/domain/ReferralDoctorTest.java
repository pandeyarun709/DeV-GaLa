package com.farmer.be.domain;

import static com.farmer.be.domain.PatientRegistrationDetailsTestSamples.*;
import static com.farmer.be.domain.ReferralDoctorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReferralDoctorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralDoctor.class);
        ReferralDoctor referralDoctor1 = getReferralDoctorSample1();
        ReferralDoctor referralDoctor2 = new ReferralDoctor();
        assertThat(referralDoctor1).isNotEqualTo(referralDoctor2);

        referralDoctor2.setId(referralDoctor1.getId());
        assertThat(referralDoctor1).isEqualTo(referralDoctor2);

        referralDoctor2 = getReferralDoctorSample2();
        assertThat(referralDoctor1).isNotEqualTo(referralDoctor2);
    }

    @Test
    void patientRegistrationDetailsTest() {
        ReferralDoctor referralDoctor = getReferralDoctorRandomSampleGenerator();
        PatientRegistrationDetails patientRegistrationDetailsBack = getPatientRegistrationDetailsRandomSampleGenerator();

        referralDoctor.addPatientRegistrationDetails(patientRegistrationDetailsBack);
        assertThat(referralDoctor.getPatientRegistrationDetails()).containsOnly(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getReferredBy()).isEqualTo(referralDoctor);

        referralDoctor.removePatientRegistrationDetails(patientRegistrationDetailsBack);
        assertThat(referralDoctor.getPatientRegistrationDetails()).doesNotContain(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getReferredBy()).isNull();

        referralDoctor.patientRegistrationDetails(new HashSet<>(Set.of(patientRegistrationDetailsBack)));
        assertThat(referralDoctor.getPatientRegistrationDetails()).containsOnly(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getReferredBy()).isEqualTo(referralDoctor);

        referralDoctor.setPatientRegistrationDetails(new HashSet<>());
        assertThat(referralDoctor.getPatientRegistrationDetails()).doesNotContain(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getReferredBy()).isNull();
    }
}
