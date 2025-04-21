package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LedgerItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LedgerItemDTO.class);
        LedgerItemDTO ledgerItemDTO1 = new LedgerItemDTO();
        ledgerItemDTO1.setId(1L);
        LedgerItemDTO ledgerItemDTO2 = new LedgerItemDTO();
        assertThat(ledgerItemDTO1).isNotEqualTo(ledgerItemDTO2);
        ledgerItemDTO2.setId(ledgerItemDTO1.getId());
        assertThat(ledgerItemDTO1).isEqualTo(ledgerItemDTO2);
        ledgerItemDTO2.setId(2L);
        assertThat(ledgerItemDTO1).isNotEqualTo(ledgerItemDTO2);
        ledgerItemDTO1.setId(null);
        assertThat(ledgerItemDTO1).isNotEqualTo(ledgerItemDTO2);
    }
}
