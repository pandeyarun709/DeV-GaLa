package com.farmer.be.web.rest;

import static com.farmer.be.domain.DiagnosticTestAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.DiagnosticTest;
import com.farmer.be.repository.DiagnosticTestRepository;
import com.farmer.be.service.dto.DiagnosticTestDTO;
import com.farmer.be.service.mapper.DiagnosticTestMapper;
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
 * Integration tests for the {@link DiagnosticTestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiagnosticTestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/diagnostic-tests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DiagnosticTestRepository diagnosticTestRepository;

    @Autowired
    private DiagnosticTestMapper diagnosticTestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiagnosticTestMockMvc;

    private DiagnosticTest diagnosticTest;

    private DiagnosticTest insertedDiagnosticTest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiagnosticTest createEntity() {
        return new DiagnosticTest()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
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
    public static DiagnosticTest createUpdatedEntity() {
        return new DiagnosticTest()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
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
        diagnosticTest = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDiagnosticTest != null) {
            diagnosticTestRepository.delete(insertedDiagnosticTest);
            insertedDiagnosticTest = null;
        }
    }

    @Test
    @Transactional
    void createDiagnosticTest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DiagnosticTest
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);
        var returnedDiagnosticTestDTO = om.readValue(
            restDiagnosticTestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diagnosticTestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DiagnosticTestDTO.class
        );

        // Validate the DiagnosticTest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDiagnosticTest = diagnosticTestMapper.toEntity(returnedDiagnosticTestDTO);
        assertDiagnosticTestUpdatableFieldsEquals(returnedDiagnosticTest, getPersistedDiagnosticTest(returnedDiagnosticTest));

        insertedDiagnosticTest = returnedDiagnosticTest;
    }

    @Test
    @Transactional
    void createDiagnosticTestWithExistingId() throws Exception {
        // Create the DiagnosticTest with an existing ID
        diagnosticTest.setId(1L);
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosticTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diagnosticTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiagnosticTests() throws Exception {
        // Initialize the database
        insertedDiagnosticTest = diagnosticTestRepository.saveAndFlush(diagnosticTest);

        // Get all the diagnosticTestList
        restDiagnosticTestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosticTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
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
    void getDiagnosticTest() throws Exception {
        // Initialize the database
        insertedDiagnosticTest = diagnosticTestRepository.saveAndFlush(diagnosticTest);

        // Get the diagnosticTest
        restDiagnosticTestMockMvc
            .perform(get(ENTITY_API_URL_ID, diagnosticTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diagnosticTest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
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
    void getNonExistingDiagnosticTest() throws Exception {
        // Get the diagnosticTest
        restDiagnosticTestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiagnosticTest() throws Exception {
        // Initialize the database
        insertedDiagnosticTest = diagnosticTestRepository.saveAndFlush(diagnosticTest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diagnosticTest
        DiagnosticTest updatedDiagnosticTest = diagnosticTestRepository.findById(diagnosticTest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDiagnosticTest are not directly saved in db
        em.detach(updatedDiagnosticTest);
        updatedDiagnosticTest
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .fee(UPDATED_FEE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(updatedDiagnosticTest);

        restDiagnosticTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diagnosticTestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(diagnosticTestDTO))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDiagnosticTestToMatchAllProperties(updatedDiagnosticTest);
    }

    @Test
    @Transactional
    void putNonExistingDiagnosticTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTest.setId(longCount.incrementAndGet());

        // Create the DiagnosticTest
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosticTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diagnosticTestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(diagnosticTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiagnosticTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTest.setId(longCount.incrementAndGet());

        // Create the DiagnosticTest
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(diagnosticTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiagnosticTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTest.setId(longCount.incrementAndGet());

        // Create the DiagnosticTest
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diagnosticTestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiagnosticTestWithPatch() throws Exception {
        // Initialize the database
        insertedDiagnosticTest = diagnosticTestRepository.saveAndFlush(diagnosticTest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diagnosticTest using partial update
        DiagnosticTest partialUpdatedDiagnosticTest = new DiagnosticTest();
        partialUpdatedDiagnosticTest.setId(diagnosticTest.getId());

        partialUpdatedDiagnosticTest
            .fee(UPDATED_FEE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restDiagnosticTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosticTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiagnosticTest))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosticTest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiagnosticTestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDiagnosticTest, diagnosticTest),
            getPersistedDiagnosticTest(diagnosticTest)
        );
    }

    @Test
    @Transactional
    void fullUpdateDiagnosticTestWithPatch() throws Exception {
        // Initialize the database
        insertedDiagnosticTest = diagnosticTestRepository.saveAndFlush(diagnosticTest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diagnosticTest using partial update
        DiagnosticTest partialUpdatedDiagnosticTest = new DiagnosticTest();
        partialUpdatedDiagnosticTest.setId(diagnosticTest.getId());

        partialUpdatedDiagnosticTest
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .fee(UPDATED_FEE)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDiagnosticTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosticTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiagnosticTest))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosticTest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiagnosticTestUpdatableFieldsEquals(partialUpdatedDiagnosticTest, getPersistedDiagnosticTest(partialUpdatedDiagnosticTest));
    }

    @Test
    @Transactional
    void patchNonExistingDiagnosticTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTest.setId(longCount.incrementAndGet());

        // Create the DiagnosticTest
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosticTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diagnosticTestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(diagnosticTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiagnosticTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTest.setId(longCount.incrementAndGet());

        // Create the DiagnosticTest
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(diagnosticTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiagnosticTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTest.setId(longCount.incrementAndGet());

        // Create the DiagnosticTest
        DiagnosticTestDTO diagnosticTestDTO = diagnosticTestMapper.toDto(diagnosticTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(diagnosticTestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiagnosticTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiagnosticTest() throws Exception {
        // Initialize the database
        insertedDiagnosticTest = diagnosticTestRepository.saveAndFlush(diagnosticTest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the diagnosticTest
        restDiagnosticTestMockMvc
            .perform(delete(ENTITY_API_URL_ID, diagnosticTest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return diagnosticTestRepository.count();
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

    protected DiagnosticTest getPersistedDiagnosticTest(DiagnosticTest diagnosticTest) {
        return diagnosticTestRepository.findById(diagnosticTest.getId()).orElseThrow();
    }

    protected void assertPersistedDiagnosticTestToMatchAllProperties(DiagnosticTest expectedDiagnosticTest) {
        assertDiagnosticTestAllPropertiesEquals(expectedDiagnosticTest, getPersistedDiagnosticTest(expectedDiagnosticTest));
    }

    protected void assertPersistedDiagnosticTestToMatchUpdatableProperties(DiagnosticTest expectedDiagnosticTest) {
        assertDiagnosticTestAllUpdatablePropertiesEquals(expectedDiagnosticTest, getPersistedDiagnosticTest(expectedDiagnosticTest));
    }
}
