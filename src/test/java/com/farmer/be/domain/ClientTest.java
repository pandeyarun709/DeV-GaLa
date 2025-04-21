package com.farmer.be.domain;

import static com.farmer.be.domain.ClientTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static com.farmer.be.domain.PatientRegistrationDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void hospitalTest() {
        Client client = getClientRandomSampleGenerator();
        Hospital hospitalBack = getHospitalRandomSampleGenerator();

        client.addHospital(hospitalBack);
        assertThat(client.getHospitals()).containsOnly(hospitalBack);
        assertThat(hospitalBack.getClient()).isEqualTo(client);

        client.removeHospital(hospitalBack);
        assertThat(client.getHospitals()).doesNotContain(hospitalBack);
        assertThat(hospitalBack.getClient()).isNull();

        client.hospitals(new HashSet<>(Set.of(hospitalBack)));
        assertThat(client.getHospitals()).containsOnly(hospitalBack);
        assertThat(hospitalBack.getClient()).isEqualTo(client);

        client.setHospitals(new HashSet<>());
        assertThat(client.getHospitals()).doesNotContain(hospitalBack);
        assertThat(hospitalBack.getClient()).isNull();
    }

    @Test
    void patientRegistrationDetailsTest() {
        Client client = getClientRandomSampleGenerator();
        PatientRegistrationDetails patientRegistrationDetailsBack = getPatientRegistrationDetailsRandomSampleGenerator();

        client.addPatientRegistrationDetails(patientRegistrationDetailsBack);
        assertThat(client.getPatientRegistrationDetails()).containsOnly(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getClient()).isEqualTo(client);

        client.removePatientRegistrationDetails(patientRegistrationDetailsBack);
        assertThat(client.getPatientRegistrationDetails()).doesNotContain(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getClient()).isNull();

        client.patientRegistrationDetails(new HashSet<>(Set.of(patientRegistrationDetailsBack)));
        assertThat(client.getPatientRegistrationDetails()).containsOnly(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getClient()).isEqualTo(client);

        client.setPatientRegistrationDetails(new HashSet<>());
        assertThat(client.getPatientRegistrationDetails()).doesNotContain(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getClient()).isNull();
    }
}
