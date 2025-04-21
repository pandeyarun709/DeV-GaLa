package com.farmer.be.web.rest;

import static com.farmer.be.domain.DoctorVisitTypeAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.DoctorVisitType;
import com.farmer.be.domain.enumeration.VisitType;
import com.farmer.be.repository.DoctorVisitTypeRepository;
import com.farmer.be.service.dto.DoctorVisitTypeDTO;
import com.farmer.be.service.mapper.DoctorVisitTypeMapper;
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
 * Integration tests for the {@link DoctorVisitTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoctorVisitTypeResourceIT {

    private static final VisitType DEFAULT_TYPE = VisitType.InpatientVisit;
    private static final VisitType UPDATED_TYPE = VisitType.OutpatientVisit;

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

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

    private static final String ENTITY_API_URL = "/api/doctor-visit-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoctorVisitTypeRepository doctorVisitTypeRepository;

    @Autowired
    private DoctorVisitTypeMapper doctorVisitTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorVisitTypeMockMvc;

    private DoctorVisitType doctorVisitType;

    private DoctorVisitType insertedDoctorVisitType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorVisitType createEntity() {
        return new DoctorVisitType()
            .type(DEFAULT_TYPE)
            .fee(DEFAULT_FEE)
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
    public static DoctorVisitType createUpdatedEntity() {
        return new DoctorVisitType()
            .type(UPDATED_TYPE)
            .fee(UPDATED_FEE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        doctorVisitType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDoctorVisitType != null) {
            doctorVisitTypeRepository.delete(insertedDoctorVisitType);
            insertedDoctorVisitType = null;
        }
    }

    @Test
    @Transactional
    void createDoctorVisitType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DoctorVisitType
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);
        var returnedDoctorVisitTypeDTO = om.readValue(
            restDoctorVisitTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctorVisitTypeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DoctorVisitTypeDTO.class
        );

        // Validate the DoctorVisitType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDoctorVisitType = doctorVisitTypeMapper.toEntity(returnedDoctorVisitTypeDTO);
        assertDoctorVisitTypeUpdatableFieldsEquals(returnedDoctorVisitType, getPersistedDoctorVisitType(returnedDoctorVisitType));

        insertedDoctorVisitType = returnedDoctorVisitType;
    }

    @Test
    @Transactional
    void createDoctorVisitTypeWithExistingId() throws Exception {
        // Create the DoctorVisitType with an existing ID
        doctorVisitType.setId(1L);
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorVisitTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctorVisitTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoctorVisitTypes() throws Exception {
        // Initialize the database
        insertedDoctorVisitType = doctorVisitTypeRepository.saveAndFlush(doctorVisitType);

        // Get all the doctorVisitTypeList
        restDoctorVisitTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorVisitType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE)))
            .andExpect(jsonPath("$.[*].isInsuranceCovered").value(hasItem(DEFAULT_IS_INSURANCE_COVERED)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getDoctorVisitType() throws Exception {
        // Initialize the database
        insertedDoctorVisitType = doctorVisitTypeRepository.saveAndFlush(doctorVisitType);

        // Get the doctorVisitType
        restDoctorVisitTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, doctorVisitType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctorVisitType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE))
            .andExpect(jsonPath("$.isInsuranceCovered").value(DEFAULT_IS_INSURANCE_COVERED))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDoctorVisitType() throws Exception {
        // Get the doctorVisitType
        restDoctorVisitTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctorVisitType() throws Exception {
        // Initialize the database
        insertedDoctorVisitType = doctorVisitTypeRepository.saveAndFlush(doctorVisitType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctorVisitType
        DoctorVisitType updatedDoctorVisitType = doctorVisitTypeRepository.findById(doctorVisitType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoctorVisitType are not directly saved in db
        em.detach(updatedDoctorVisitType);
        updatedDoctorVisitType
            .type(UPDATED_TYPE)
            .fee(UPDATED_FEE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(updatedDoctorVisitType);

        restDoctorVisitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorVisitTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doctorVisitTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoctorVisitTypeToMatchAllProperties(updatedDoctorVisitType);
    }

    @Test
    @Transactional
    void putNonExistingDoctorVisitType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisitType.setId(longCount.incrementAndGet());

        // Create the DoctorVisitType
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorVisitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorVisitTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doctorVisitTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctorVisitType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisitType.setId(longCount.incrementAndGet());

        // Create the DoctorVisitType
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doctorVisitTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctorVisitType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisitType.setId(longCount.incrementAndGet());

        // Create the DoctorVisitType
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctorVisitTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorVisitTypeWithPatch() throws Exception {
        // Initialize the database
        insertedDoctorVisitType = doctorVisitTypeRepository.saveAndFlush(doctorVisitType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctorVisitType using partial update
        DoctorVisitType partialUpdatedDoctorVisitType = new DoctorVisitType();
        partialUpdatedDoctorVisitType.setId(doctorVisitType.getId());

        partialUpdatedDoctorVisitType
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDoctorVisitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorVisitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoctorVisitType))
            )
            .andExpect(status().isOk());

        // Validate the DoctorVisitType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoctorVisitTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDoctorVisitType, doctorVisitType),
            getPersistedDoctorVisitType(doctorVisitType)
        );
    }

    @Test
    @Transactional
    void fullUpdateDoctorVisitTypeWithPatch() throws Exception {
        // Initialize the database
        insertedDoctorVisitType = doctorVisitTypeRepository.saveAndFlush(doctorVisitType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctorVisitType using partial update
        DoctorVisitType partialUpdatedDoctorVisitType = new DoctorVisitType();
        partialUpdatedDoctorVisitType.setId(doctorVisitType.getId());

        partialUpdatedDoctorVisitType
            .type(UPDATED_TYPE)
            .fee(UPDATED_FEE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDoctorVisitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorVisitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoctorVisitType))
            )
            .andExpect(status().isOk());

        // Validate the DoctorVisitType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoctorVisitTypeUpdatableFieldsEquals(
            partialUpdatedDoctorVisitType,
            getPersistedDoctorVisitType(partialUpdatedDoctorVisitType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDoctorVisitType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisitType.setId(longCount.incrementAndGet());

        // Create the DoctorVisitType
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorVisitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctorVisitTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doctorVisitTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctorVisitType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisitType.setId(longCount.incrementAndGet());

        // Create the DoctorVisitType
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doctorVisitTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctorVisitType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisitType.setId(longCount.incrementAndGet());

        // Create the DoctorVisitType
        DoctorVisitTypeDTO doctorVisitTypeDTO = doctorVisitTypeMapper.toDto(doctorVisitType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doctorVisitTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorVisitType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctorVisitType() throws Exception {
        // Initialize the database
        insertedDoctorVisitType = doctorVisitTypeRepository.saveAndFlush(doctorVisitType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doctorVisitType
        restDoctorVisitTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctorVisitType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doctorVisitTypeRepository.count();
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

    protected DoctorVisitType getPersistedDoctorVisitType(DoctorVisitType doctorVisitType) {
        return doctorVisitTypeRepository.findById(doctorVisitType.getId()).orElseThrow();
    }

    protected void assertPersistedDoctorVisitTypeToMatchAllProperties(DoctorVisitType expectedDoctorVisitType) {
        assertDoctorVisitTypeAllPropertiesEquals(expectedDoctorVisitType, getPersistedDoctorVisitType(expectedDoctorVisitType));
    }

    protected void assertPersistedDoctorVisitTypeToMatchUpdatableProperties(DoctorVisitType expectedDoctorVisitType) {
        assertDoctorVisitTypeAllUpdatablePropertiesEquals(expectedDoctorVisitType, getPersistedDoctorVisitType(expectedDoctorVisitType));
    }
}
