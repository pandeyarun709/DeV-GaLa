package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static com.farmer.be.domain.PatientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void hospitalTest() {
        Address address = getAddressRandomSampleGenerator();
        Hospital hospitalBack = getHospitalRandomSampleGenerator();

        address.setHospital(hospitalBack);
        assertThat(address.getHospital()).isEqualTo(hospitalBack);
        assertThat(hospitalBack.getAddress()).isEqualTo(address);

        address.hospital(null);
        assertThat(address.getHospital()).isNull();
        assertThat(hospitalBack.getAddress()).isNull();
    }

    @Test
    void patientTest() {
        Address address = getAddressRandomSampleGenerator();
        Patient patientBack = getPatientRandomSampleGenerator();

        address.setPatient(patientBack);
        assertThat(address.getPatient()).isEqualTo(patientBack);
        assertThat(patientBack.getAddress()).isEqualTo(address);

        address.patient(null);
        assertThat(address.getPatient()).isNull();
        assertThat(patientBack.getAddress()).isNull();
    }
}
