package com.farmer.be.domain;

import static com.farmer.be.domain.DiagnosticTestTestSamples.*;
import static com.farmer.be.domain.DoctorVisitTypeTestSamples.*;
import static com.farmer.be.domain.SlotTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SlotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Slot.class);
        Slot slot1 = getSlotSample1();
        Slot slot2 = new Slot();
        assertThat(slot1).isNotEqualTo(slot2);

        slot2.setId(slot1.getId());
        assertThat(slot1).isEqualTo(slot2);

        slot2 = getSlotSample2();
        assertThat(slot1).isNotEqualTo(slot2);
    }

    @Test
    void doctorVisitTypeTest() {
        Slot slot = getSlotRandomSampleGenerator();
        DoctorVisitType doctorVisitTypeBack = getDoctorVisitTypeRandomSampleGenerator();

        slot.setDoctorVisitType(doctorVisitTypeBack);
        assertThat(slot.getDoctorVisitType()).isEqualTo(doctorVisitTypeBack);

        slot.doctorVisitType(null);
        assertThat(slot.getDoctorVisitType()).isNull();
    }

    @Test
    void testTest() {
        Slot slot = getSlotRandomSampleGenerator();
        DiagnosticTest diagnosticTestBack = getDiagnosticTestRandomSampleGenerator();

        slot.setTest(diagnosticTestBack);
        assertThat(slot.getTest()).isEqualTo(diagnosticTestBack);

        slot.test(null);
        assertThat(slot.getTest()).isNull();
    }
}
