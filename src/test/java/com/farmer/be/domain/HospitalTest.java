package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.BedTestSamples.*;
import static com.farmer.be.domain.ClientTestSamples.*;
import static com.farmer.be.domain.DepartmentTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static com.farmer.be.domain.MedicineBatchTestSamples.*;
import static com.farmer.be.domain.PatientRegistrationDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HospitalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hospital.class);
        Hospital hospital1 = getHospitalSample1();
        Hospital hospital2 = new Hospital();
        assertThat(hospital1).isNotEqualTo(hospital2);

        hospital2.setId(hospital1.getId());
        assertThat(hospital1).isEqualTo(hospital2);

        hospital2 = getHospitalSample2();
        assertThat(hospital1).isNotEqualTo(hospital2);
    }

    @Test
    void addressTest() {
        Hospital hospital = getHospitalRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        hospital.setAddress(addressBack);
        assertThat(hospital.getAddress()).isEqualTo(addressBack);

        hospital.address(null);
        assertThat(hospital.getAddress()).isNull();
    }

    @Test
    void patientRegistrationDetailsTest() {
        Hospital hospital = getHospitalRandomSampleGenerator();
        PatientRegistrationDetails patientRegistrationDetailsBack = getPatientRegistrationDetailsRandomSampleGenerator();

        hospital.addPatientRegistrationDetails(patientRegistrationDetailsBack);
        assertThat(hospital.getPatientRegistrationDetails()).containsOnly(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getHospital()).isEqualTo(hospital);

        hospital.removePatientRegistrationDetails(patientRegistrationDetailsBack);
        assertThat(hospital.getPatientRegistrationDetails()).doesNotContain(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getHospital()).isNull();

        hospital.patientRegistrationDetails(new HashSet<>(Set.of(patientRegistrationDetailsBack)));
        assertThat(hospital.getPatientRegistrationDetails()).containsOnly(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getHospital()).isEqualTo(hospital);

        hospital.setPatientRegistrationDetails(new HashSet<>());
        assertThat(hospital.getPatientRegistrationDetails()).doesNotContain(patientRegistrationDetailsBack);
        assertThat(patientRegistrationDetailsBack.getHospital()).isNull();
    }

    @Test
    void departmentTest() {
        Hospital hospital = getHospitalRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        hospital.addDepartment(departmentBack);
        assertThat(hospital.getDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getHospital()).isEqualTo(hospital);

        hospital.removeDepartment(departmentBack);
        assertThat(hospital.getDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getHospital()).isNull();

        hospital.departments(new HashSet<>(Set.of(departmentBack)));
        assertThat(hospital.getDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getHospital()).isEqualTo(hospital);

        hospital.setDepartments(new HashSet<>());
        assertThat(hospital.getDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getHospital()).isNull();
    }

    @Test
    void bedTest() {
        Hospital hospital = getHospitalRandomSampleGenerator();
        Bed bedBack = getBedRandomSampleGenerator();

        hospital.addBed(bedBack);
        assertThat(hospital.getBeds()).containsOnly(bedBack);
        assertThat(bedBack.getHospital()).isEqualTo(hospital);

        hospital.removeBed(bedBack);
        assertThat(hospital.getBeds()).doesNotContain(bedBack);
        assertThat(bedBack.getHospital()).isNull();

        hospital.beds(new HashSet<>(Set.of(bedBack)));
        assertThat(hospital.getBeds()).containsOnly(bedBack);
        assertThat(bedBack.getHospital()).isEqualTo(hospital);

        hospital.setBeds(new HashSet<>());
        assertThat(hospital.getBeds()).doesNotContain(bedBack);
        assertThat(bedBack.getHospital()).isNull();
    }

    @Test
    void medicineBatchTest() {
        Hospital hospital = getHospitalRandomSampleGenerator();
        MedicineBatch medicineBatchBack = getMedicineBatchRandomSampleGenerator();

        hospital.addMedicineBatch(medicineBatchBack);
        assertThat(hospital.getMedicineBatches()).containsOnly(medicineBatchBack);
        assertThat(medicineBatchBack.getHospital()).isEqualTo(hospital);

        hospital.removeMedicineBatch(medicineBatchBack);
        assertThat(hospital.getMedicineBatches()).doesNotContain(medicineBatchBack);
        assertThat(medicineBatchBack.getHospital()).isNull();

        hospital.medicineBatches(new HashSet<>(Set.of(medicineBatchBack)));
        assertThat(hospital.getMedicineBatches()).containsOnly(medicineBatchBack);
        assertThat(medicineBatchBack.getHospital()).isEqualTo(hospital);

        hospital.setMedicineBatches(new HashSet<>());
        assertThat(hospital.getMedicineBatches()).doesNotContain(medicineBatchBack);
        assertThat(medicineBatchBack.getHospital()).isNull();
    }

    @Test
    void admissionTest() {
        Hospital hospital = getHospitalRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        hospital.addAdmission(admissionBack);
        assertThat(hospital.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getHospital()).isEqualTo(hospital);

        hospital.removeAdmission(admissionBack);
        assertThat(hospital.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getHospital()).isNull();

        hospital.admissions(new HashSet<>(Set.of(admissionBack)));
        assertThat(hospital.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getHospital()).isEqualTo(hospital);

        hospital.setAdmissions(new HashSet<>());
        assertThat(hospital.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getHospital()).isNull();
    }

    @Test
    void clientTest() {
        Hospital hospital = getHospitalRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        hospital.setClient(clientBack);
        assertThat(hospital.getClient()).isEqualTo(clientBack);

        hospital.client(null);
        assertThat(hospital.getClient()).isNull();
    }
}
