package com.farmer.be.web.rest;

import static com.farmer.be.domain.PatientRegistrationDetailsAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.PatientRegistrationDetails;
import com.farmer.be.repository.PatientRegistrationDetailsRepository;
import com.farmer.be.service.dto.PatientRegistrationDetailsDTO;
import com.farmer.be.service.mapper.PatientRegistrationDetailsMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link PatientRegistrationDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientRegistrationDetailsResourceIT {

    private static final String DEFAULT_REGISTRATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/patient-registration-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PatientRegistrationDetailsRepository patientRegistrationDetailsRepository;

    @Autowired
    private PatientRegistrationDetailsMapper patientRegistrationDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientRegistrationDetailsMockMvc;

    private PatientRegistrationDetails patientRegistrationDetails;

    private PatientRegistrationDetails insertedPatientRegistrationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientRegistrationDetails createEntity() {
        return new PatientRegistrationDetails().registrationId(DEFAULT_REGISTRATION_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientRegistrationDetails createUpdatedEntity() {
        return new PatientRegistrationDetails().registrationId(UPDATED_REGISTRATION_ID);
    }

    @BeforeEach
    void initTest() {
        patientRegistrationDetails = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPatientRegistrationDetails != null) {
            patientRegistrationDetailsRepository.delete(insertedPatientRegistrationDetails);
            insertedPatientRegistrationDetails = null;
        }
    }

    @Test
    @Transactional
    void createPatientRegistrationDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PatientRegistrationDetails
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);
        var returnedPatientRegistrationDetailsDTO = om.readValue(
            restPatientRegistrationDetailsMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PatientRegistrationDetailsDTO.class
        );

        // Validate the PatientRegistrationDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPatientRegistrationDetails = patientRegistrationDetailsMapper.toEntity(returnedPatientRegistrationDetailsDTO);
        assertPatientRegistrationDetailsUpdatableFieldsEquals(
            returnedPatientRegistrationDetails,
            getPersistedPatientRegistrationDetails(returnedPatientRegistrationDetails)
        );

        insertedPatientRegistrationDetails = returnedPatientRegistrationDetails;
    }

    @Test
    @Transactional
    void createPatientRegistrationDetailsWithExistingId() throws Exception {
        // Create the PatientRegistrationDetails with an existing ID
        patientRegistrationDetails.setId(1L);
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientRegistrationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatientRegistrationDetails() throws Exception {
        // Initialize the database
        insertedPatientRegistrationDetails = patientRegistrationDetailsRepository.saveAndFlush(patientRegistrationDetails);

        // Get all the patientRegistrationDetailsList
        restPatientRegistrationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientRegistrationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].registrationId").value(hasItem(DEFAULT_REGISTRATION_ID)));
    }

    @Test
    @Transactional
    void getPatientRegistrationDetails() throws Exception {
        // Initialize the database
        insertedPatientRegistrationDetails = patientRegistrationDetailsRepository.saveAndFlush(patientRegistrationDetails);

        // Get the patientRegistrationDetails
        restPatientRegistrationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, patientRegistrationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientRegistrationDetails.getId().intValue()))
            .andExpect(jsonPath("$.registrationId").value(DEFAULT_REGISTRATION_ID));
    }

    @Test
    @Transactional
    void getNonExistingPatientRegistrationDetails() throws Exception {
        // Get the patientRegistrationDetails
        restPatientRegistrationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatientRegistrationDetails() throws Exception {
        // Initialize the database
        insertedPatientRegistrationDetails = patientRegistrationDetailsRepository.saveAndFlush(patientRegistrationDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the patientRegistrationDetails
        PatientRegistrationDetails updatedPatientRegistrationDetails = patientRegistrationDetailsRepository
            .findById(patientRegistrationDetails.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPatientRegistrationDetails are not directly saved in db
        em.detach(updatedPatientRegistrationDetails);
        updatedPatientRegistrationDetails.registrationId(UPDATED_REGISTRATION_ID);
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(
            updatedPatientRegistrationDetails
        );

        restPatientRegistrationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientRegistrationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPatientRegistrationDetailsToMatchAllProperties(updatedPatientRegistrationDetails);
    }

    @Test
    @Transactional
    void putNonExistingPatientRegistrationDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        patientRegistrationDetails.setId(longCount.incrementAndGet());

        // Create the PatientRegistrationDetails
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientRegistrationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientRegistrationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatientRegistrationDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        patientRegistrationDetails.setId(longCount.incrementAndGet());

        // Create the PatientRegistrationDetails
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientRegistrationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatientRegistrationDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        patientRegistrationDetails.setId(longCount.incrementAndGet());

        // Create the PatientRegistrationDetails
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientRegistrationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientRegistrationDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedPatientRegistrationDetails = patientRegistrationDetailsRepository.saveAndFlush(patientRegistrationDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the patientRegistrationDetails using partial update
        PatientRegistrationDetails partialUpdatedPatientRegistrationDetails = new PatientRegistrationDetails();
        partialUpdatedPatientRegistrationDetails.setId(patientRegistrationDetails.getId());

        restPatientRegistrationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientRegistrationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPatientRegistrationDetails))
            )
            .andExpect(status().isOk());

        // Validate the PatientRegistrationDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPatientRegistrationDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPatientRegistrationDetails, patientRegistrationDetails),
            getPersistedPatientRegistrationDetails(patientRegistrationDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdatePatientRegistrationDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedPatientRegistrationDetails = patientRegistrationDetailsRepository.saveAndFlush(patientRegistrationDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the patientRegistrationDetails using partial update
        PatientRegistrationDetails partialUpdatedPatientRegistrationDetails = new PatientRegistrationDetails();
        partialUpdatedPatientRegistrationDetails.setId(patientRegistrationDetails.getId());

        partialUpdatedPatientRegistrationDetails.registrationId(UPDATED_REGISTRATION_ID);

        restPatientRegistrationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientRegistrationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPatientRegistrationDetails))
            )
            .andExpect(status().isOk());

        // Validate the PatientRegistrationDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPatientRegistrationDetailsUpdatableFieldsEquals(
            partialUpdatedPatientRegistrationDetails,
            getPersistedPatientRegistrationDetails(partialUpdatedPatientRegistrationDetails)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPatientRegistrationDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        patientRegistrationDetails.setId(longCount.incrementAndGet());

        // Create the PatientRegistrationDetails
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientRegistrationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientRegistrationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatientRegistrationDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        patientRegistrationDetails.setId(longCount.incrementAndGet());

        // Create the PatientRegistrationDetails
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientRegistrationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatientRegistrationDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        patientRegistrationDetails.setId(longCount.incrementAndGet());

        // Create the PatientRegistrationDetails
        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientRegistrationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(patientRegistrationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientRegistrationDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatientRegistrationDetails() throws Exception {
        // Initialize the database
        insertedPatientRegistrationDetails = patientRegistrationDetailsRepository.saveAndFlush(patientRegistrationDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the patientRegistrationDetails
        restPatientRegistrationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, patientRegistrationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return patientRegistrationDetailsRepository.count();
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

    protected PatientRegistrationDetails getPersistedPatientRegistrationDetails(PatientRegistrationDetails patientRegistrationDetails) {
        return patientRegistrationDetailsRepository.findById(patientRegistrationDetails.getId()).orElseThrow();
    }

    protected void assertPersistedPatientRegistrationDetailsToMatchAllProperties(
        PatientRegistrationDetails expectedPatientRegistrationDetails
    ) {
        assertPatientRegistrationDetailsAllPropertiesEquals(
            expectedPatientRegistrationDetails,
            getPersistedPatientRegistrationDetails(expectedPatientRegistrationDetails)
        );
    }

    protected void assertPersistedPatientRegistrationDetailsToMatchUpdatableProperties(
        PatientRegistrationDetails expectedPatientRegistrationDetails
    ) {
        assertPatientRegistrationDetailsAllUpdatablePropertiesEquals(
            expectedPatientRegistrationDetails,
            getPersistedPatientRegistrationDetails(expectedPatientRegistrationDetails)
        );
    }
}
