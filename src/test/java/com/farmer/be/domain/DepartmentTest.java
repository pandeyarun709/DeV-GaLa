package com.farmer.be.domain;

import static com.farmer.be.domain.DepartmentTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestTestSamples.*;
import static com.farmer.be.domain.EmployeeTestSamples.*;
import static com.farmer.be.domain.HospitalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DepartmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Department.class);
        Department department1 = getDepartmentSample1();
        Department department2 = new Department();
        assertThat(department1).isNotEqualTo(department2);

        department2.setId(department1.getId());
        assertThat(department1).isEqualTo(department2);

        department2 = getDepartmentSample2();
        assertThat(department1).isNotEqualTo(department2);
    }

    @Test
    void employeeTest() {
        Department department = getDepartmentRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        department.addEmployee(employeeBack);
        assertThat(department.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartment()).isEqualTo(department);

        department.removeEmployee(employeeBack);
        assertThat(department.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartment()).isNull();

        department.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(department.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartment()).isEqualTo(department);

        department.setEmployees(new HashSet<>());
        assertThat(department.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartment()).isNull();
    }

    @Test
    void diagnosticTestTest() {
        Department department = getDepartmentRandomSampleGenerator();
        DiagnosticTest diagnosticTestBack = getDiagnosticTestRandomSampleGenerator();

        department.addDiagnosticTest(diagnosticTestBack);
        assertThat(department.getDiagnosticTests()).containsOnly(diagnosticTestBack);
        assertThat(diagnosticTestBack.getDepartment()).isEqualTo(department);

        department.removeDiagnosticTest(diagnosticTestBack);
        assertThat(department.getDiagnosticTests()).doesNotContain(diagnosticTestBack);
        assertThat(diagnosticTestBack.getDepartment()).isNull();

        department.diagnosticTests(new HashSet<>(Set.of(diagnosticTestBack)));
        assertThat(department.getDiagnosticTests()).containsOnly(diagnosticTestBack);
        assertThat(diagnosticTestBack.getDepartment()).isEqualTo(department);

        department.setDiagnosticTests(new HashSet<>());
        assertThat(department.getDiagnosticTests()).doesNotContain(diagnosticTestBack);
        assertThat(diagnosticTestBack.getDepartment()).isNull();
    }

    @Test
    void hospitalTest() {
        Department department = getDepartmentRandomSampleGenerator();
        Hospital hospitalBack = getHospitalRandomSampleGenerator();

        department.setHospital(hospitalBack);
        assertThat(department.getHospital()).isEqualTo(hospitalBack);

        department.hospital(null);
        assertThat(department.getHospital()).isNull();
    }
}
