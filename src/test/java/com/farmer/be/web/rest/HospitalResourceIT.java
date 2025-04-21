package com.farmer.be.web.rest;

import static com.farmer.be.domain.HospitalAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Hospital;
import com.farmer.be.repository.HospitalRepository;
import com.farmer.be.service.dto.HospitalDTO;
import com.farmer.be.service.mapper.HospitalMapper;
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
 * Integration tests for the {@link HospitalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HospitalResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_PATH = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/hospitals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHospitalMockMvc;

    private Hospital hospital;

    private Hospital insertedHospital;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createEntity() {
        return new Hospital()
            .name(DEFAULT_NAME)
            .logoPath(DEFAULT_LOGO_PATH)
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
    public static Hospital createUpdatedEntity() {
        return new Hospital()
            .name(UPDATED_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        hospital = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedHospital != null) {
            hospitalRepository.delete(insertedHospital);
            insertedHospital = null;
        }
    }

    @Test
    @Transactional
    void createHospital() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);
        var returnedHospitalDTO = om.readValue(
            restHospitalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hospitalDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HospitalDTO.class
        );

        // Validate the Hospital in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHospital = hospitalMapper.toEntity(returnedHospitalDTO);
        assertHospitalUpdatableFieldsEquals(returnedHospital, getPersistedHospital(returnedHospital));

        insertedHospital = returnedHospital;
    }

    @Test
    @Transactional
    void createHospitalWithExistingId() throws Exception {
        // Create the Hospital with an existing ID
        hospital.setId(1L);
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHospitals() throws Exception {
        // Initialize the database
        insertedHospital = hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getHospital() throws Exception {
        // Initialize the database
        insertedHospital = hospitalRepository.saveAndFlush(hospital);

        // Get the hospital
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL_ID, hospital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hospital.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logoPath").value(DEFAULT_LOGO_PATH))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHospital() throws Exception {
        // Get the hospital
        restHospitalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHospital() throws Exception {
        // Initialize the database
        insertedHospital = hospitalRepository.saveAndFlush(hospital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hospital
        Hospital updatedHospital = hospitalRepository.findById(hospital.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHospital are not directly saved in db
        em.detach(updatedHospital);
        updatedHospital
            .name(UPDATED_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        HospitalDTO hospitalDTO = hospitalMapper.toDto(updatedHospital);

        restHospitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hospitalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hospitalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHospitalToMatchAllProperties(updatedHospital);
    }

    @Test
    @Transactional
    void putNonExistingHospital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hospital.setId(longCount.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hospitalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHospital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hospital.setId(longCount.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHospital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hospital.setId(longCount.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hospitalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHospitalWithPatch() throws Exception {
        // Initialize the database
        insertedHospital = hospitalRepository.saveAndFlush(hospital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hospital using partial update
        Hospital partialUpdatedHospital = new Hospital();
        partialUpdatedHospital.setId(hospital.getId());

        partialUpdatedHospital.createdBy(UPDATED_CREATED_BY).updatedBy(UPDATED_UPDATED_BY).updatedOn(UPDATED_UPDATED_ON);

        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHospital.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHospital))
            )
            .andExpect(status().isOk());

        // Validate the Hospital in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHospitalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHospital, hospital), getPersistedHospital(hospital));
    }

    @Test
    @Transactional
    void fullUpdateHospitalWithPatch() throws Exception {
        // Initialize the database
        insertedHospital = hospitalRepository.saveAndFlush(hospital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hospital using partial update
        Hospital partialUpdatedHospital = new Hospital();
        partialUpdatedHospital.setId(hospital.getId());

        partialUpdatedHospital
            .name(UPDATED_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHospital.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHospital))
            )
            .andExpect(status().isOk());

        // Validate the Hospital in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHospitalUpdatableFieldsEquals(partialUpdatedHospital, getPersistedHospital(partialUpdatedHospital));
    }

    @Test
    @Transactional
    void patchNonExistingHospital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hospital.setId(longCount.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hospitalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHospital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hospital.setId(longCount.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHospital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hospital.setId(longCount.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hospitalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hospital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHospital() throws Exception {
        // Initialize the database
        insertedHospital = hospitalRepository.saveAndFlush(hospital);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hospital
        restHospitalMockMvc
            .perform(delete(ENTITY_API_URL_ID, hospital.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hospitalRepository.count();
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

    protected Hospital getPersistedHospital(Hospital hospital) {
        return hospitalRepository.findById(hospital.getId()).orElseThrow();
    }

    protected void assertPersistedHospitalToMatchAllProperties(Hospital expectedHospital) {
        assertHospitalAllPropertiesEquals(expectedHospital, getPersistedHospital(expectedHospital));
    }

    protected void assertPersistedHospitalToMatchUpdatableProperties(Hospital expectedHospital) {
        assertHospitalAllUpdatablePropertiesEquals(expectedHospital, getPersistedHospital(expectedHospital));
    }
}
