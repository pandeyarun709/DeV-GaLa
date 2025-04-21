package com.farmer.be.web.rest;

import static com.farmer.be.domain.PrescriptionAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Prescription;
import com.farmer.be.repository.PrescriptionRepository;
import com.farmer.be.service.dto.PrescriptionDTO;
import com.farmer.be.service.mapper.PrescriptionMapper;
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
 * Integration tests for the {@link PrescriptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrescriptionResourceIT {

    private static final String DEFAULT_HISTORY = "AAAAAAAAAA";
    private static final String UPDATED_HISTORY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLIANT = "AAAAAAAAAA";
    private static final String UPDATED_COMPLIANT = "BBBBBBBBBB";

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_BP_HIGH = 1D;
    private static final Double UPDATED_BP_HIGH = 2D;

    private static final Double DEFAULT_BP_LOW = 1D;
    private static final Double UPDATED_BP_LOW = 2D;

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;

    private static final String DEFAULT_OTHER_VITAL = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_VITAL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/prescriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrescriptionMockMvc;

    private Prescription prescription;

    private Prescription insertedPrescription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prescription createEntity() {
        return new Prescription()
            .history(DEFAULT_HISTORY)
            .compliant(DEFAULT_COMPLIANT)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .bpHigh(DEFAULT_BP_HIGH)
            .bpLow(DEFAULT_BP_LOW)
            .temperature(DEFAULT_TEMPERATURE)
            .otherVital(DEFAULT_OTHER_VITAL)
            .description(DEFAULT_DESCRIPTION)
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
    public static Prescription createUpdatedEntity() {
        return new Prescription()
            .history(UPDATED_HISTORY)
            .compliant(UPDATED_COMPLIANT)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .bpHigh(UPDATED_BP_HIGH)
            .bpLow(UPDATED_BP_LOW)
            .temperature(UPDATED_TEMPERATURE)
            .otherVital(UPDATED_OTHER_VITAL)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        prescription = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPrescription != null) {
            prescriptionRepository.delete(insertedPrescription);
            insertedPrescription = null;
        }
    }

    @Test
    @Transactional
    void createPrescription() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prescription
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);
        var returnedPrescriptionDTO = om.readValue(
            restPrescriptionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prescriptionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PrescriptionDTO.class
        );

        // Validate the Prescription in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPrescription = prescriptionMapper.toEntity(returnedPrescriptionDTO);
        assertPrescriptionUpdatableFieldsEquals(returnedPrescription, getPersistedPrescription(returnedPrescription));

        insertedPrescription = returnedPrescription;
    }

    @Test
    @Transactional
    void createPrescriptionWithExistingId() throws Exception {
        // Create the Prescription with an existing ID
        prescription.setId(1L);
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescriptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrescriptions() throws Exception {
        // Initialize the database
        insertedPrescription = prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList
        restPrescriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].history").value(hasItem(DEFAULT_HISTORY)))
            .andExpect(jsonPath("$.[*].compliant").value(hasItem(DEFAULT_COMPLIANT)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].bpHigh").value(hasItem(DEFAULT_BP_HIGH)))
            .andExpect(jsonPath("$.[*].bpLow").value(hasItem(DEFAULT_BP_LOW)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)))
            .andExpect(jsonPath("$.[*].otherVital").value(hasItem(DEFAULT_OTHER_VITAL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getPrescription() throws Exception {
        // Initialize the database
        insertedPrescription = prescriptionRepository.saveAndFlush(prescription);

        // Get the prescription
        restPrescriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, prescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prescription.getId().intValue()))
            .andExpect(jsonPath("$.history").value(DEFAULT_HISTORY))
            .andExpect(jsonPath("$.compliant").value(DEFAULT_COMPLIANT))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.bpHigh").value(DEFAULT_BP_HIGH))
            .andExpect(jsonPath("$.bpLow").value(DEFAULT_BP_LOW))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE))
            .andExpect(jsonPath("$.otherVital").value(DEFAULT_OTHER_VITAL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrescription() throws Exception {
        // Get the prescription
        restPrescriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrescription() throws Exception {
        // Initialize the database
        insertedPrescription = prescriptionRepository.saveAndFlush(prescription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prescription
        Prescription updatedPrescription = prescriptionRepository.findById(prescription.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrescription are not directly saved in db
        em.detach(updatedPrescription);
        updatedPrescription
            .history(UPDATED_HISTORY)
            .compliant(UPDATED_COMPLIANT)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .bpHigh(UPDATED_BP_HIGH)
            .bpLow(UPDATED_BP_LOW)
            .temperature(UPDATED_TEMPERATURE)
            .otherVital(UPDATED_OTHER_VITAL)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(updatedPrescription);

        restPrescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prescriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prescriptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPrescriptionToMatchAllProperties(updatedPrescription);
    }

    @Test
    @Transactional
    void putNonExistingPrescription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prescription.setId(longCount.incrementAndGet());

        // Create the Prescription
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prescriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrescription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prescription.setId(longCount.incrementAndGet());

        // Create the Prescription
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrescription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prescription.setId(longCount.incrementAndGet());

        // Create the Prescription
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prescriptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrescriptionWithPatch() throws Exception {
        // Initialize the database
        insertedPrescription = prescriptionRepository.saveAndFlush(prescription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prescription using partial update
        Prescription partialUpdatedPrescription = new Prescription();
        partialUpdatedPrescription.setId(prescription.getId());

        partialUpdatedPrescription
            .bpHigh(UPDATED_BP_HIGH)
            .otherVital(UPDATED_OTHER_VITAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPrescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrescription.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrescription))
            )
            .andExpect(status().isOk());

        // Validate the Prescription in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrescriptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPrescription, prescription),
            getPersistedPrescription(prescription)
        );
    }

    @Test
    @Transactional
    void fullUpdatePrescriptionWithPatch() throws Exception {
        // Initialize the database
        insertedPrescription = prescriptionRepository.saveAndFlush(prescription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prescription using partial update
        Prescription partialUpdatedPrescription = new Prescription();
        partialUpdatedPrescription.setId(prescription.getId());

        partialUpdatedPrescription
            .history(UPDATED_HISTORY)
            .compliant(UPDATED_COMPLIANT)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .bpHigh(UPDATED_BP_HIGH)
            .bpLow(UPDATED_BP_LOW)
            .temperature(UPDATED_TEMPERATURE)
            .otherVital(UPDATED_OTHER_VITAL)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restPrescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrescription.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrescription))
            )
            .andExpect(status().isOk());

        // Validate the Prescription in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrescriptionUpdatableFieldsEquals(partialUpdatedPrescription, getPersistedPrescription(partialUpdatedPrescription));
    }

    @Test
    @Transactional
    void patchNonExistingPrescription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prescription.setId(longCount.incrementAndGet());

        // Create the Prescription
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prescriptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrescription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prescription.setId(longCount.incrementAndGet());

        // Create the Prescription
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrescription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prescription.setId(longCount.incrementAndGet());

        // Create the Prescription
        PrescriptionDTO prescriptionDTO = prescriptionMapper.toDto(prescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prescriptionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prescription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrescription() throws Exception {
        // Initialize the database
        insertedPrescription = prescriptionRepository.saveAndFlush(prescription);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prescription
        restPrescriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, prescription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prescriptionRepository.count();
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

    protected Prescription getPersistedPrescription(Prescription prescription) {
        return prescriptionRepository.findById(prescription.getId()).orElseThrow();
    }

    protected void assertPersistedPrescriptionToMatchAllProperties(Prescription expectedPrescription) {
        assertPrescriptionAllPropertiesEquals(expectedPrescription, getPersistedPrescription(expectedPrescription));
    }

    protected void assertPersistedPrescriptionToMatchUpdatableProperties(Prescription expectedPrescription) {
        assertPrescriptionAllUpdatablePropertiesEquals(expectedPrescription, getPersistedPrescription(expectedPrescription));
    }
}
