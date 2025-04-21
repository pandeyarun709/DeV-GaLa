package com.farmer.be.web.rest;

import static com.farmer.be.domain.BedAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Bed;
import com.farmer.be.domain.enumeration.BedType;
import com.farmer.be.repository.BedRepository;
import com.farmer.be.service.dto.BedDTO;
import com.farmer.be.service.mapper.BedMapper;
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
 * Integration tests for the {@link BedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BedResourceIT {

    private static final BedType DEFAULT_TYPE = BedType.ICU;
    private static final BedType UPDATED_TYPE = BedType.Supirior;

    private static final Long DEFAULT_FLOOR = 1L;
    private static final Long UPDATED_FLOOR = 2L;

    private static final Long DEFAULT_ROOM_NO = 1L;
    private static final Long UPDATED_ROOM_NO = 2L;

    private static final Double DEFAULT_DAILY_RATE = 1D;
    private static final Double UPDATED_DAILY_RATE = 2D;

    private static final Boolean DEFAULT_IS_INSURANCE_COVERED = false;
    private static final Boolean UPDATED_IS_INSURANCE_COVERED = true;

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

    private static final String ENTITY_API_URL = "/api/beds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private BedMapper bedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBedMockMvc;

    private Bed bed;

    private Bed insertedBed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bed createEntity() {
        return new Bed()
            .type(DEFAULT_TYPE)
            .floor(DEFAULT_FLOOR)
            .roomNo(DEFAULT_ROOM_NO)
            .dailyRate(DEFAULT_DAILY_RATE)
            .isInsuranceCovered(DEFAULT_IS_INSURANCE_COVERED)
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
    public static Bed createUpdatedEntity() {
        return new Bed()
            .type(UPDATED_TYPE)
            .floor(UPDATED_FLOOR)
            .roomNo(UPDATED_ROOM_NO)
            .dailyRate(UPDATED_DAILY_RATE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        bed = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBed != null) {
            bedRepository.delete(insertedBed);
            insertedBed = null;
        }
    }

    @Test
    @Transactional
    void createBed() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bed
        BedDTO bedDTO = bedMapper.toDto(bed);
        var returnedBedDTO = om.readValue(
            restBedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BedDTO.class
        );

        // Validate the Bed in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBed = bedMapper.toEntity(returnedBedDTO);
        assertBedUpdatableFieldsEquals(returnedBed, getPersistedBed(returnedBed));

        insertedBed = returnedBed;
    }

    @Test
    @Transactional
    void createBedWithExistingId() throws Exception {
        // Create the Bed with an existing ID
        bed.setId(1L);
        BedDTO bedDTO = bedMapper.toDto(bed);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBeds() throws Exception {
        // Initialize the database
        insertedBed = bedRepository.saveAndFlush(bed);

        // Get all the bedList
        restBedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bed.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR.intValue())))
            .andExpect(jsonPath("$.[*].roomNo").value(hasItem(DEFAULT_ROOM_NO.intValue())))
            .andExpect(jsonPath("$.[*].dailyRate").value(hasItem(DEFAULT_DAILY_RATE)))
            .andExpect(jsonPath("$.[*].isInsuranceCovered").value(hasItem(DEFAULT_IS_INSURANCE_COVERED)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getBed() throws Exception {
        // Initialize the database
        insertedBed = bedRepository.saveAndFlush(bed);

        // Get the bed
        restBedMockMvc
            .perform(get(ENTITY_API_URL_ID, bed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bed.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR.intValue()))
            .andExpect(jsonPath("$.roomNo").value(DEFAULT_ROOM_NO.intValue()))
            .andExpect(jsonPath("$.dailyRate").value(DEFAULT_DAILY_RATE))
            .andExpect(jsonPath("$.isInsuranceCovered").value(DEFAULT_IS_INSURANCE_COVERED))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBed() throws Exception {
        // Get the bed
        restBedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBed() throws Exception {
        // Initialize the database
        insertedBed = bedRepository.saveAndFlush(bed);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bed
        Bed updatedBed = bedRepository.findById(bed.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBed are not directly saved in db
        em.detach(updatedBed);
        updatedBed
            .type(UPDATED_TYPE)
            .floor(UPDATED_FLOOR)
            .roomNo(UPDATED_ROOM_NO)
            .dailyRate(UPDATED_DAILY_RATE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        BedDTO bedDTO = bedMapper.toDto(updatedBed);

        restBedMockMvc
            .perform(put(ENTITY_API_URL_ID, bedDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedDTO)))
            .andExpect(status().isOk());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBedToMatchAllProperties(updatedBed);
    }

    @Test
    @Transactional
    void putNonExistingBed() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bed.setId(longCount.incrementAndGet());

        // Create the Bed
        BedDTO bedDTO = bedMapper.toDto(bed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedMockMvc
            .perform(put(ENTITY_API_URL_ID, bedDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBed() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bed.setId(longCount.incrementAndGet());

        // Create the Bed
        BedDTO bedDTO = bedMapper.toDto(bed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBed() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bed.setId(longCount.incrementAndGet());

        // Create the Bed
        BedDTO bedDTO = bedMapper.toDto(bed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bedDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBedWithPatch() throws Exception {
        // Initialize the database
        insertedBed = bedRepository.saveAndFlush(bed);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bed using partial update
        Bed partialUpdatedBed = new Bed();
        partialUpdatedBed.setId(bed.getId());

        partialUpdatedBed.type(UPDATED_TYPE).roomNo(UPDATED_ROOM_NO).createdBy(UPDATED_CREATED_BY).createdOn(UPDATED_CREATED_ON);

        restBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBed.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBed))
            )
            .andExpect(status().isOk());

        // Validate the Bed in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBedUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBed, bed), getPersistedBed(bed));
    }

    @Test
    @Transactional
    void fullUpdateBedWithPatch() throws Exception {
        // Initialize the database
        insertedBed = bedRepository.saveAndFlush(bed);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bed using partial update
        Bed partialUpdatedBed = new Bed();
        partialUpdatedBed.setId(bed.getId());

        partialUpdatedBed
            .type(UPDATED_TYPE)
            .floor(UPDATED_FLOOR)
            .roomNo(UPDATED_ROOM_NO)
            .dailyRate(UPDATED_DAILY_RATE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBed.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBed))
            )
            .andExpect(status().isOk());

        // Validate the Bed in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBedUpdatableFieldsEquals(partialUpdatedBed, getPersistedBed(partialUpdatedBed));
    }

    @Test
    @Transactional
    void patchNonExistingBed() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bed.setId(longCount.incrementAndGet());

        // Create the Bed
        BedDTO bedDTO = bedMapper.toDto(bed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bedDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBed() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bed.setId(longCount.incrementAndGet());

        // Create the Bed
        BedDTO bedDTO = bedMapper.toDto(bed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBed() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bed.setId(longCount.incrementAndGet());

        // Create the Bed
        BedDTO bedDTO = bedMapper.toDto(bed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bedDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bed in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBed() throws Exception {
        // Initialize the database
        insertedBed = bedRepository.saveAndFlush(bed);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bed
        restBedMockMvc.perform(delete(ENTITY_API_URL_ID, bed.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bedRepository.count();
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

    protected Bed getPersistedBed(Bed bed) {
        return bedRepository.findById(bed.getId()).orElseThrow();
    }

    protected void assertPersistedBedToMatchAllProperties(Bed expectedBed) {
        assertBedAllPropertiesEquals(expectedBed, getPersistedBed(expectedBed));
    }

    protected void assertPersistedBedToMatchUpdatableProperties(Bed expectedBed) {
        assertBedAllUpdatablePropertiesEquals(expectedBed, getPersistedBed(expectedBed));
    }
}
