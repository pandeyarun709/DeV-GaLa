package com.farmer.be.domain;

import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.DepartmentTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTypeTestSamples.*;
import static com.farmer.be.domain.EmployeeTestSamples.*;
import static com.farmer.be.domain.QualificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void doctorVisitTypeTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        DoctorVisitType doctorVisitTypeBack = getDoctorVisitTypeRandomSampleGenerator();

        employee.addDoctorVisitType(doctorVisitTypeBack);
        assertThat(employee.getDoctorVisitTypes()).containsOnly(doctorVisitTypeBack);
        assertThat(doctorVisitTypeBack.getDoctor()).isEqualTo(employee);

        employee.removeDoctorVisitType(doctorVisitTypeBack);
        assertThat(employee.getDoctorVisitTypes()).doesNotContain(doctorVisitTypeBack);
        assertThat(doctorVisitTypeBack.getDoctor()).isNull();

        employee.doctorVisitTypes(new HashSet<>(Set.of(doctorVisitTypeBack)));
        assertThat(employee.getDoctorVisitTypes()).containsOnly(doctorVisitTypeBack);
        assertThat(doctorVisitTypeBack.getDoctor()).isEqualTo(employee);

        employee.setDoctorVisitTypes(new HashSet<>());
        assertThat(employee.getDoctorVisitTypes()).doesNotContain(doctorVisitTypeBack);
        assertThat(doctorVisitTypeBack.getDoctor()).isNull();
    }

    @Test
    void admissionTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        employee.addAdmission(admissionBack);
        assertThat(employee.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getAdmittedUnder()).isEqualTo(employee);

        employee.removeAdmission(admissionBack);
        assertThat(employee.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getAdmittedUnder()).isNull();

        employee.admissions(new HashSet<>(Set.of(admissionBack)));
        assertThat(employee.getAdmissions()).containsOnly(admissionBack);
        assertThat(admissionBack.getAdmittedUnder()).isEqualTo(employee);

        employee.setAdmissions(new HashSet<>());
        assertThat(employee.getAdmissions()).doesNotContain(admissionBack);
        assertThat(admissionBack.getAdmittedUnder()).isNull();
    }

    @Test
    void qualificationsTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Qualification qualificationBack = getQualificationRandomSampleGenerator();

        employee.addQualifications(qualificationBack);
        assertThat(employee.getQualifications()).containsOnly(qualificationBack);

        employee.removeQualifications(qualificationBack);
        assertThat(employee.getQualifications()).doesNotContain(qualificationBack);

        employee.qualifications(new HashSet<>(Set.of(qualificationBack)));
        assertThat(employee.getQualifications()).containsOnly(qualificationBack);

        employee.setQualifications(new HashSet<>());
        assertThat(employee.getQualifications()).doesNotContain(qualificationBack);
    }

    @Test
    void departmentTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        employee.setDepartment(departmentBack);
        assertThat(employee.getDepartment()).isEqualTo(departmentBack);

        employee.department(null);
        assertThat(employee.getDepartment()).isNull();
    }

    @Test
    void testTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        DiagnosticTest diagnosticTestBack = getDiagnosticTestRandomSampleGenerator();

        employee.setTest(diagnosticTestBack);
        assertThat(employee.getTest()).isEqualTo(diagnosticTestBack);

        employee.test(null);
        assertThat(employee.getTest()).isNull();
    }
}
