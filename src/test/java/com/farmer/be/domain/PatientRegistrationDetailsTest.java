package com.farmer.be.domain;

import static com.farmer.be.domain.ClientTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static com.farmer.be.domain.PatientRegistrationDetailsTestSamples.*;
import static com.farmer.be.domain.ReferralDoctorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientRegistrationDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientRegistrationDetails.class);
        PatientRegistrationDetails patientRegistrationDetails1 = getPatientRegistrationDetailsSample1();
        PatientRegistrationDetails patientRegistrationDetails2 = new PatientRegistrationDetails();
        assertThat(patientRegistrationDetails1).isNotEqualTo(patientRegistrationDetails2);

        patientRegistrationDetails2.setId(patientRegistrationDetails1.getId());
        assertThat(patientRegistrationDetails1).isEqualTo(patientRegistrationDetails2);

        patientRegistrationDetails2 = getPatientRegistrationDetailsSample2();
        assertThat(patientRegistrationDetails1).isNotEqualTo(patientRegistrationDetails2);
    }

    @Test
    void clientTest() {
        PatientRegistrationDetails patientRegistrationDetails = getPatientRegistrationDetailsRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        patientRegistrationDetails.setClient(clientBack);
        assertThat(patientRegistrationDetails.getClient()).isEqualTo(clientBack);

        patientRegistrationDetails.client(null);
        assertThat(patientRegistrationDetails.getClient()).isNull();
    }

    @Test
    void hospitalTest() {
        PatientRegistrationDetails patientRegistrationDetails = getPatientRegistrationDetailsRandomSampleGenerator();
        Hospital hospitalBack = getHospitalRandomSampleGenerator();

        patientRegistrationDetails.setHospital(hospitalBack);
        assertThat(patientRegistrationDetails.getHospital()).isEqualTo(hospitalBack);

        patientRegistrationDetails.hospital(null);
        assertThat(patientRegistrationDetails.getHospital()).isNull();
    }

    @Test
    void referredByTest() {
        PatientRegistrationDetails patientRegistrationDetails = getPatientRegistrationDetailsRandomSampleGenerator();
        ReferralDoctor referralDoctorBack = getReferralDoctorRandomSampleGenerator();

        patientRegistrationDetails.setReferredBy(referralDoctorBack);
        assertThat(patientRegistrationDetails.getReferredBy()).isEqualTo(referralDoctorBack);

        patientRegistrationDetails.referredBy(null);
        assertThat(patientRegistrationDetails.getReferredBy()).isNull();
    }
}
