package com.farmer.be.domain;

import static com.farmer.be.domain.AdmissionTestSamples.*;
import static com.farmer.be.domain.LedgerItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LedgerItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LedgerItem.class);
        LedgerItem ledgerItem1 = getLedgerItemSample1();
        LedgerItem ledgerItem2 = new LedgerItem();
        assertThat(ledgerItem1).isNotEqualTo(ledgerItem2);

        ledgerItem2.setId(ledgerItem1.getId());
        assertThat(ledgerItem1).isEqualTo(ledgerItem2);

        ledgerItem2 = getLedgerItemSample2();
        assertThat(ledgerItem1).isNotEqualTo(ledgerItem2);
    }

    @Test
    void admissionTest() {
        LedgerItem ledgerItem = getLedgerItemRandomSampleGenerator();
        Admission admissionBack = getAdmissionRandomSampleGenerator();

        ledgerItem.setAdmission(admissionBack);
        assertThat(ledgerItem.getAdmission()).isEqualTo(admissionBack);

        ledgerItem.admission(null);
        assertThat(ledgerItem.getAdmission()).isNull();
    }
}
