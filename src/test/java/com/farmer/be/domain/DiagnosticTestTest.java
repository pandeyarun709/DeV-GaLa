package com.farmer.be.domain;

import static com.farmer.be.domain.DepartmentTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestReportTestSamples.*;
import static com.farmer.be.domain.DiagnosticTestTestSamples.*;
import static com.farmer.be.domain.EmployeeTestSamples.*;
import static com.farmer.be.domain.PrescriptionTestSamples.*;
import static com.farmer.be.domain.SlotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DiagnosticTestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosticTest.class);
        DiagnosticTest diagnosticTest1 = getDiagnosticTestSample1();
        DiagnosticTest diagnosticTest2 = new DiagnosticTest();
        assertThat(diagnosticTest1).isNotEqualTo(diagnosticTest2);

        diagnosticTest2.setId(diagnosticTest1.getId());
        assertThat(diagnosticTest1).isEqualTo(diagnosticTest2);

        diagnosticTest2 = getDiagnosticTestSample2();
        assertThat(diagnosticTest1).isNotEqualTo(diagnosticTest2);
    }

    @Test
    void employeeTest() {
        DiagnosticTest diagnosticTest = getDiagnosticTestRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        diagnosticTest.addEmployee(employeeBack);
        assertThat(diagnosticTest.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getTest()).isEqualTo(diagnosticTest);

        diagnosticTest.removeEmployee(employeeBack);
        assertThat(diagnosticTest.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getTest()).isNull();

        diagnosticTest.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(diagnosticTest.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getTest()).isEqualTo(diagnosticTest);

        diagnosticTest.setEmployees(new HashSet<>());
        assertThat(diagnosticTest.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getTest()).isNull();
    }

    @Test
    void diagnosticTestReportTest() {
        DiagnosticTest diagnosticTest = getDiagnosticTestRandomSampleGenerator();
        DiagnosticTestReport diagnosticTestReportBack = getDiagnosticTestReportRandomSampleGenerator();

        diagnosticTest.addDiagnosticTestReport(diagnosticTestReportBack);
        assertThat(diagnosticTest.getDiagnosticTestReports()).containsOnly(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getTest()).isEqualTo(diagnosticTest);

        diagnosticTest.removeDiagnosticTestReport(diagnosticTestReportBack);
        assertThat(diagnosticTest.getDiagnosticTestReports()).doesNotContain(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getTest()).isNull();

        diagnosticTest.diagnosticTestReports(new HashSet<>(Set.of(diagnosticTestReportBack)));
        assertThat(diagnosticTest.getDiagnosticTestReports()).containsOnly(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getTest()).isEqualTo(diagnosticTest);

        diagnosticTest.setDiagnosticTestReports(new HashSet<>());
        assertThat(diagnosticTest.getDiagnosticTestReports()).doesNotContain(diagnosticTestReportBack);
        assertThat(diagnosticTestReportBack.getTest()).isNull();
    }

    @Test
    void slotTest() {
        DiagnosticTest diagnosticTest = getDiagnosticTestRandomSampleGenerator();
        Slot slotBack = getSlotRandomSampleGenerator();

        diagnosticTest.addSlot(slotBack);
        assertThat(diagnosticTest.getSlots()).containsOnly(slotBack);
        assertThat(slotBack.getTest()).isEqualTo(diagnosticTest);

        diagnosticTest.removeSlot(slotBack);
        assertThat(diagnosticTest.getSlots()).doesNotContain(slotBack);
        assertThat(slotBack.getTest()).isNull();

        diagnosticTest.slots(new HashSet<>(Set.of(slotBack)));
        assertThat(diagnosticTest.getSlots()).containsOnly(slotBack);
        assertThat(slotBack.getTest()).isEqualTo(diagnosticTest);

        diagnosticTest.setSlots(new HashSet<>());
        assertThat(diagnosticTest.getSlots()).doesNotContain(slotBack);
        assertThat(slotBack.getTest()).isNull();
    }

    @Test
    void departmentTest() {
        DiagnosticTest diagnosticTest = getDiagnosticTestRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        diagnosticTest.setDepartment(departmentBack);
        assertThat(diagnosticTest.getDepartment()).isEqualTo(departmentBack);

        diagnosticTest.department(null);
        assertThat(diagnosticTest.getDepartment()).isNull();
    }

    @Test
    void prescriptionTest() {
        DiagnosticTest diagnosticTest = getDiagnosticTestRandomSampleGenerator();
        Prescription prescriptionBack = getPrescriptionRandomSampleGenerator();

        diagnosticTest.setPrescription(prescriptionBack);
        assertThat(diagnosticTest.getPrescription()).isEqualTo(prescriptionBack);

        diagnosticTest.prescription(null);
        assertThat(diagnosticTest.getPrescription()).isNull();
    }
}
