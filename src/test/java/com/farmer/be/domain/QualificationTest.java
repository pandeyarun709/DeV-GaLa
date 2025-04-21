package com.farmer.be.domain;

import static com.farmer.be.domain.EmployeeTestSamples.*;
import static com.farmer.be.domain.QualificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class QualificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualification.class);
        Qualification qualification1 = getQualificationSample1();
        Qualification qualification2 = new Qualification();
        assertThat(qualification1).isNotEqualTo(qualification2);

        qualification2.setId(qualification1.getId());
        assertThat(qualification1).isEqualTo(qualification2);

        qualification2 = getQualificationSample2();
        assertThat(qualification1).isNotEqualTo(qualification2);
    }

    @Test
    void employeesTest() {
        Qualification qualification = getQualificationRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        qualification.addEmployees(employeeBack);
        assertThat(qualification.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getQualifications()).containsOnly(qualification);

        qualification.removeEmployees(employeeBack);
        assertThat(qualification.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getQualifications()).doesNotContain(qualification);

        qualification.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(qualification.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getQualifications()).containsOnly(qualification);

        qualification.setEmployees(new HashSet<>());
        assertThat(qualification.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getQualifications()).doesNotContain(qualification);
    }
}
