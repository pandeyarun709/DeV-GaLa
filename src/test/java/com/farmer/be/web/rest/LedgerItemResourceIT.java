package com.farmer.be.web.rest;

import static com.farmer.be.domain.LedgerItemAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.LedgerItem;
import com.farmer.be.repository.LedgerItemRepository;
import com.farmer.be.service.dto.LedgerItemDTO;
import com.farmer.be.service.mapper.LedgerItemMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LedgerItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LedgerItemResourceIT {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    private static final Boolean DEFAULT_IS_COVERED_BY_INSURANCE = false;
    private static final Boolean UPDATED_IS_COVERED_BY_INSURANCE = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ledger-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LedgerItemRepository ledgerItemRepository;

    @Autowired
    private LedgerItemMapper ledgerItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLedgerItemMockMvc;

    private LedgerItem ledgerItem;

    private LedgerItem insertedLedgerItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LedgerItem createEntity() {
        return new LedgerItem()
            .time(DEFAULT_TIME)
            .description(DEFAULT_DESCRIPTION)
            .fee(DEFAULT_FEE)
            .isCoveredByInsurance(DEFAULT_IS_COVERED_BY_INSURANCE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LedgerItem createUpdatedEntity() {
        return new LedgerItem()
            .time(UPDATED_TIME)
            .description(UPDATED_DESCRIPTION)
            .fee(UPDATED_FEE)
            .isCoveredByInsurance(UPDATED_IS_COVERED_BY_INSURANCE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        ledgerItem = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedLedgerItem != null) {
            ledgerItemRepository.delete(insertedLedgerItem);
            insertedLedgerItem = null;
        }
    }

    @Test
    @Transactional
    void createLedgerItem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LedgerItem
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);
        var returnedLedgerItemDTO = om.readValue(
            restLedgerItemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ledgerItemDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LedgerItemDTO.class
        );

        // Validate the LedgerItem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLedgerItem = ledgerItemMapper.toEntity(returnedLedgerItemDTO);
        assertLedgerItemUpdatableFieldsEquals(returnedLedgerItem, getPersistedLedgerItem(returnedLedgerItem));

        insertedLedgerItem = returnedLedgerItem;
    }

    @Test
    @Transactional
    void createLedgerItemWithExistingId() throws Exception {
        // Create the LedgerItem with an existing ID
        ledgerItem.setId(1L);
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLedgerItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ledgerItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLedgerItems() throws Exception {
        // Initialize the database
        insertedLedgerItem = ledgerItemRepository.saveAndFlush(ledgerItem);

        // Get all the ledgerItemList
        restLedgerItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ledgerItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE)))
            .andExpect(jsonPath("$.[*].isCoveredByInsurance").value(hasItem(DEFAULT_IS_COVERED_BY_INSURANCE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getLedgerItem() throws Exception {
        // Initialize the database
        insertedLedgerItem = ledgerItemRepository.saveAndFlush(ledgerItem);

        // Get the ledgerItem
        restLedgerItemMockMvc
            .perform(get(ENTITY_API_URL_ID, ledgerItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ledgerItem.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE))
            .andExpect(jsonPath("$.isCoveredByInsurance").value(DEFAULT_IS_COVERED_BY_INSURANCE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLedgerItem() throws Exception {
        // Get the ledgerItem
        restLedgerItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLedgerItem() throws Exception {
        // Initialize the database
        insertedLedgerItem = ledgerItemRepository.saveAndFlush(ledgerItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ledgerItem
        LedgerItem updatedLedgerItem = ledgerItemRepository.findById(ledgerItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLedgerItem are not directly saved in db
        em.detach(updatedLedgerItem);
        updatedLedgerItem
            .time(UPDATED_TIME)
            .description(UPDATED_DESCRIPTION)
            .fee(UPDATED_FEE)
            .isCoveredByInsurance(UPDATED_IS_COVERED_BY_INSURANCE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(updatedLedgerItem);

        restLedgerItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ledgerItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ledgerItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLedgerItemToMatchAllProperties(updatedLedgerItem);
    }

    @Test
    @Transactional
    void putNonExistingLedgerItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ledgerItem.setId(longCount.incrementAndGet());

        // Create the LedgerItem
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLedgerItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ledgerItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ledgerItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLedgerItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ledgerItem.setId(longCount.incrementAndGet());

        // Create the LedgerItem
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ledgerItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLedgerItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ledgerItem.setId(longCount.incrementAndGet());

        // Create the LedgerItem
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ledgerItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLedgerItemWithPatch() throws Exception {
        // Initialize the database
        insertedLedgerItem = ledgerItemRepository.saveAndFlush(ledgerItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ledgerItem using partial update
        LedgerItem partialUpdatedLedgerItem = new LedgerItem();
        partialUpdatedLedgerItem.setId(ledgerItem.getId());

        partialUpdatedLedgerItem
            .time(UPDATED_TIME)
            .description(UPDATED_DESCRIPTION)
            .fee(UPDATED_FEE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restLedgerItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLedgerItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLedgerItem))
            )
            .andExpect(status().isOk());

        // Validate the LedgerItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLedgerItemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLedgerItem, ledgerItem),
            getPersistedLedgerItem(ledgerItem)
        );
    }

    @Test
    @Transactional
    void fullUpdateLedgerItemWithPatch() throws Exception {
        // Initialize the database
        insertedLedgerItem = ledgerItemRepository.saveAndFlush(ledgerItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ledgerItem using partial update
        LedgerItem partialUpdatedLedgerItem = new LedgerItem();
        partialUpdatedLedgerItem.setId(ledgerItem.getId());

        partialUpdatedLedgerItem
            .time(UPDATED_TIME)
            .description(UPDATED_DESCRIPTION)
            .fee(UPDATED_FEE)
            .isCoveredByInsurance(UPDATED_IS_COVERED_BY_INSURANCE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restLedgerItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLedgerItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLedgerItem))
            )
            .andExpect(status().isOk());

        // Validate the LedgerItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLedgerItemUpdatableFieldsEquals(partialUpdatedLedgerItem, getPersistedLedgerItem(partialUpdatedLedgerItem));
    }

    @Test
    @Transactional
    void patchNonExistingLedgerItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ledgerItem.setId(longCount.incrementAndGet());

        // Create the LedgerItem
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLedgerItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ledgerItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ledgerItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLedgerItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ledgerItem.setId(longCount.incrementAndGet());

        // Create the LedgerItem
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ledgerItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLedgerItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ledgerItem.setId(longCount.incrementAndGet());

        // Create the LedgerItem
        LedgerItemDTO ledgerItemDTO = ledgerItemMapper.toDto(ledgerItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ledgerItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LedgerItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLedgerItem() throws Exception {
        // Initialize the database
        insertedLedgerItem = ledgerItemRepository.saveAndFlush(ledgerItem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ledgerItem
        restLedgerItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, ledgerItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ledgerItemRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected LedgerItem getPersistedLedgerItem(LedgerItem ledgerItem) {
        return ledgerItemRepository.findById(ledgerItem.getId()).orElseThrow();
    }

    protected void assertPersistedLedgerItemToMatchAllProperties(LedgerItem expectedLedgerItem) {
        assertLedgerItemAllPropertiesEquals(expectedLedgerItem, getPersistedLedgerItem(expectedLedgerItem));
    }

    protected void assertPersistedLedgerItemToMatchUpdatableProperties(LedgerItem expectedLedgerItem) {
        assertLedgerItemAllUpdatablePropertiesEquals(expectedLedgerItem, getPersistedLedgerItem(expectedLedgerItem));
    }
}
