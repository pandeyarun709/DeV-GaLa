package com.farmer.be.web.rest;

import static com.farmer.be.domain.MedicineBatchAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.MedicineBatch;
import com.farmer.be.repository.MedicineBatchRepository;
import com.farmer.be.service.dto.MedicineBatchDTO;
import com.farmer.be.service.mapper.MedicineBatchMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link MedicineBatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicineBatchResourceIT {

    private static final String DEFAULT_BATCH_NO = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Double DEFAULT_SELLING_PRICE = 1D;
    private static final Double UPDATED_SELLING_PRICE = 2D;

    private static final String DEFAULT_STORAGE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_STORAGE_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_RACK_NO = "AAAAAAAAAA";
    private static final String UPDATED_RACK_NO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/medicine-batches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MedicineBatchRepository medicineBatchRepository;

    @Autowired
    private MedicineBatchMapper medicineBatchMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicineBatchMockMvc;

    private MedicineBatch medicineBatch;

    private MedicineBatch insertedMedicineBatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicineBatch createEntity() {
        return new MedicineBatch()
            .batchNo(DEFAULT_BATCH_NO)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .quantity(DEFAULT_QUANTITY)
            .sellingPrice(DEFAULT_SELLING_PRICE)
            .storageLocation(DEFAULT_STORAGE_LOCATION)
            .rackNo(DEFAULT_RACK_NO)
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
    public static MedicineBatch createUpdatedEntity() {
        return new MedicineBatch()
            .batchNo(UPDATED_BATCH_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .quantity(UPDATED_QUANTITY)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .storageLocation(UPDATED_STORAGE_LOCATION)
            .rackNo(UPDATED_RACK_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        medicineBatch = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMedicineBatch != null) {
            medicineBatchRepository.delete(insertedMedicineBatch);
            insertedMedicineBatch = null;
        }
    }

    @Test
    @Transactional
    void createMedicineBatch() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MedicineBatch
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);
        var returnedMedicineBatchDTO = om.readValue(
            restMedicineBatchMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineBatchDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MedicineBatchDTO.class
        );

        // Validate the MedicineBatch in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMedicineBatch = medicineBatchMapper.toEntity(returnedMedicineBatchDTO);
        assertMedicineBatchUpdatableFieldsEquals(returnedMedicineBatch, getPersistedMedicineBatch(returnedMedicineBatch));

        insertedMedicineBatch = returnedMedicineBatch;
    }

    @Test
    @Transactional
    void createMedicineBatchWithExistingId() throws Exception {
        // Create the MedicineBatch with an existing ID
        medicineBatch.setId(1L);
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineBatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineBatchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedicineBatches() throws Exception {
        // Initialize the database
        insertedMedicineBatch = medicineBatchRepository.saveAndFlush(medicineBatch);

        // Get all the medicineBatchList
        restMedicineBatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicineBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].batchNo").value(hasItem(DEFAULT_BATCH_NO)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE)))
            .andExpect(jsonPath("$.[*].storageLocation").value(hasItem(DEFAULT_STORAGE_LOCATION)))
            .andExpect(jsonPath("$.[*].rackNo").value(hasItem(DEFAULT_RACK_NO)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getMedicineBatch() throws Exception {
        // Initialize the database
        insertedMedicineBatch = medicineBatchRepository.saveAndFlush(medicineBatch);

        // Get the medicineBatch
        restMedicineBatchMockMvc
            .perform(get(ENTITY_API_URL_ID, medicineBatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicineBatch.getId().intValue()))
            .andExpect(jsonPath("$.batchNo").value(DEFAULT_BATCH_NO))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE))
            .andExpect(jsonPath("$.storageLocation").value(DEFAULT_STORAGE_LOCATION))
            .andExpect(jsonPath("$.rackNo").value(DEFAULT_RACK_NO))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMedicineBatch() throws Exception {
        // Get the medicineBatch
        restMedicineBatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedicineBatch() throws Exception {
        // Initialize the database
        insertedMedicineBatch = medicineBatchRepository.saveAndFlush(medicineBatch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicineBatch
        MedicineBatch updatedMedicineBatch = medicineBatchRepository.findById(medicineBatch.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMedicineBatch are not directly saved in db
        em.detach(updatedMedicineBatch);
        updatedMedicineBatch
            .batchNo(UPDATED_BATCH_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .quantity(UPDATED_QUANTITY)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .storageLocation(UPDATED_STORAGE_LOCATION)
            .rackNo(UPDATED_RACK_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(updatedMedicineBatch);

        restMedicineBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicineBatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineBatchDTO))
            )
            .andExpect(status().isOk());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMedicineBatchToMatchAllProperties(updatedMedicineBatch);
    }

    @Test
    @Transactional
    void putNonExistingMedicineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineBatch.setId(longCount.incrementAndGet());

        // Create the MedicineBatch
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicineBatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineBatch.setId(longCount.incrementAndGet());

        // Create the MedicineBatch
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineBatch.setId(longCount.incrementAndGet());

        // Create the MedicineBatch
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineBatchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineBatchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicineBatchWithPatch() throws Exception {
        // Initialize the database
        insertedMedicineBatch = medicineBatchRepository.saveAndFlush(medicineBatch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicineBatch using partial update
        MedicineBatch partialUpdatedMedicineBatch = new MedicineBatch();
        partialUpdatedMedicineBatch.setId(medicineBatch.getId());

        partialUpdatedMedicineBatch
            .sellingPrice(UPDATED_SELLING_PRICE)
            .storageLocation(UPDATED_STORAGE_LOCATION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON);

        restMedicineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicineBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedicineBatch))
            )
            .andExpect(status().isOk());

        // Validate the MedicineBatch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicineBatchUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMedicineBatch, medicineBatch),
            getPersistedMedicineBatch(medicineBatch)
        );
    }

    @Test
    @Transactional
    void fullUpdateMedicineBatchWithPatch() throws Exception {
        // Initialize the database
        insertedMedicineBatch = medicineBatchRepository.saveAndFlush(medicineBatch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicineBatch using partial update
        MedicineBatch partialUpdatedMedicineBatch = new MedicineBatch();
        partialUpdatedMedicineBatch.setId(medicineBatch.getId());

        partialUpdatedMedicineBatch
            .batchNo(UPDATED_BATCH_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .quantity(UPDATED_QUANTITY)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .storageLocation(UPDATED_STORAGE_LOCATION)
            .rackNo(UPDATED_RACK_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restMedicineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicineBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedicineBatch))
            )
            .andExpect(status().isOk());

        // Validate the MedicineBatch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicineBatchUpdatableFieldsEquals(partialUpdatedMedicineBatch, getPersistedMedicineBatch(partialUpdatedMedicineBatch));
    }

    @Test
    @Transactional
    void patchNonExistingMedicineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineBatch.setId(longCount.incrementAndGet());

        // Create the MedicineBatch
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicineBatchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medicineBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineBatch.setId(longCount.incrementAndGet());

        // Create the MedicineBatch
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medicineBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicineBatch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineBatch.setId(longCount.incrementAndGet());

        // Create the MedicineBatch
        MedicineBatchDTO medicineBatchDTO = medicineBatchMapper.toDto(medicineBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineBatchMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(medicineBatchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicineBatch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicineBatch() throws Exception {
        // Initialize the database
        insertedMedicineBatch = medicineBatchRepository.saveAndFlush(medicineBatch);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the medicineBatch
        restMedicineBatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicineBatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return medicineBatchRepository.count();
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

    protected MedicineBatch getPersistedMedicineBatch(MedicineBatch medicineBatch) {
        return medicineBatchRepository.findById(medicineBatch.getId()).orElseThrow();
    }

    protected void assertPersistedMedicineBatchToMatchAllProperties(MedicineBatch expectedMedicineBatch) {
        assertMedicineBatchAllPropertiesEquals(expectedMedicineBatch, getPersistedMedicineBatch(expectedMedicineBatch));
    }

    protected void assertPersistedMedicineBatchToMatchUpdatableProperties(MedicineBatch expectedMedicineBatch) {
        assertMedicineBatchAllUpdatablePropertiesEquals(expectedMedicineBatch, getPersistedMedicineBatch(expectedMedicineBatch));
    }
}
