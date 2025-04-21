package com.farmer.be.domain;

import static com.farmer.be.domain.DoctorVisitTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTypeTestSamples.*;
import static com.farmer.be.domain.EmployeeTestSamples.*;
import static com.farmer.be.domain.SlotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DoctorVisitTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorVisitType.class);
        DoctorVisitType doctorVisitType1 = getDoctorVisitTypeSample1();
        DoctorVisitType doctorVisitType2 = new DoctorVisitType();
        assertThat(doctorVisitType1).isNotEqualTo(doctorVisitType2);

        doctorVisitType2.setId(doctorVisitType1.getId());
        assertThat(doctorVisitType1).isEqualTo(doctorVisitType2);

        doctorVisitType2 = getDoctorVisitTypeSample2();
        assertThat(doctorVisitType1).isNotEqualTo(doctorVisitType2);
    }

    @Test
    void slotTest() {
        DoctorVisitType doctorVisitType = getDoctorVisitTypeRandomSampleGenerator();
        Slot slotBack = getSlotRandomSampleGenerator();

        doctorVisitType.addSlot(slotBack);
        assertThat(doctorVisitType.getSlots()).containsOnly(slotBack);
        assertThat(slotBack.getDoctorVisitType()).isEqualTo(doctorVisitType);

        doctorVisitType.removeSlot(slotBack);
        assertThat(doctorVisitType.getSlots()).doesNotContain(slotBack);
        assertThat(slotBack.getDoctorVisitType()).isNull();

        doctorVisitType.slots(new HashSet<>(Set.of(slotBack)));
        assertThat(doctorVisitType.getSlots()).containsOnly(slotBack);
        assertThat(slotBack.getDoctorVisitType()).isEqualTo(doctorVisitType);

        doctorVisitType.setSlots(new HashSet<>());
        assertThat(doctorVisitType.getSlots()).doesNotContain(slotBack);
        assertThat(slotBack.getDoctorVisitType()).isNull();
    }

    @Test
    void doctorVisitTest() {
        DoctorVisitType doctorVisitType = getDoctorVisitTypeRandomSampleGenerator();
        DoctorVisit doctorVisitBack = getDoctorVisitRandomSampleGenerator();

        doctorVisitType.addDoctorVisit(doctorVisitBack);
        assertThat(doctorVisitType.getDoctorVisits()).containsOnly(doctorVisitBack);
        assertThat(doctorVisitBack.getDoctorVisitType()).isEqualTo(doctorVisitType);

        doctorVisitType.removeDoctorVisit(doctorVisitBack);
        assertThat(doctorVisitType.getDoctorVisits()).doesNotContain(doctorVisitBack);
        assertThat(doctorVisitBack.getDoctorVisitType()).isNull();

        doctorVisitType.doctorVisits(new HashSet<>(Set.of(doctorVisitBack)));
        assertThat(doctorVisitType.getDoctorVisits()).containsOnly(doctorVisitBack);
        assertThat(doctorVisitBack.getDoctorVisitType()).isEqualTo(doctorVisitType);

        doctorVisitType.setDoctorVisits(new HashSet<>());
        assertThat(doctorVisitType.getDoctorVisits()).doesNotContain(doctorVisitBack);
        assertThat(doctorVisitBack.getDoctorVisitType()).isNull();
    }

    @Test
    void doctorTest() {
        DoctorVisitType doctorVisitType = getDoctorVisitTypeRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        doctorVisitType.setDoctor(employeeBack);
        assertThat(doctorVisitType.getDoctor()).isEqualTo(employeeBack);

        doctorVisitType.doctor(null);
        assertThat(doctorVisitType.getDoctor()).isNull();
    }
}
