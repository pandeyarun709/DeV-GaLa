package com.farmer.be.web.rest;

import static com.farmer.be.domain.DiagnosticTestReportAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.DiagnosticTestReport;
import com.farmer.be.repository.DiagnosticTestReportRepository;
import com.farmer.be.service.dto.DiagnosticTestReportDTO;
import com.farmer.be.service.mapper.DiagnosticTestReportMapper;
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
 * Integration tests for the {@link DiagnosticTestReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiagnosticTestReportResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SIGNED_BY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/diagnostic-test-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DiagnosticTestReportRepository diagnosticTestReportRepository;

    @Autowired
    private DiagnosticTestReportMapper diagnosticTestReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiagnosticTestReportMockMvc;

    private DiagnosticTestReport diagnosticTestReport;

    private DiagnosticTestReport insertedDiagnosticTestReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiagnosticTestReport createEntity() {
        return new DiagnosticTestReport()
            .description(DEFAULT_DESCRIPTION)
            .signedBy(DEFAULT_SIGNED_BY)
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
    public static DiagnosticTestReport createUpdatedEntity() {
        return new DiagnosticTestReport()
            .description(UPDATED_DESCRIPTION)
            .signedBy(UPDATED_SIGNED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        diagnosticTestReport = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDiagnosticTestReport != null) {
            diagnosticTestReportRepository.delete(insertedDiagnosticTestReport);
            insertedDiagnosticTestReport = null;
        }
    }

    @Test
    @Transactional
    void createDiagnosticTestReport() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DiagnosticTestReport
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);
        var returnedDiagnosticTestReportDTO = om.readValue(
            restDiagnosticTestReportMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diagnosticTestReportDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DiagnosticTestReportDTO.class
        );

        // Validate the DiagnosticTestReport in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDiagnosticTestReport = diagnosticTestReportMapper.toEntity(returnedDiagnosticTestReportDTO);
        assertDiagnosticTestReportUpdatableFieldsEquals(
            returnedDiagnosticTestReport,
            getPersistedDiagnosticTestReport(returnedDiagnosticTestReport)
        );

        insertedDiagnosticTestReport = returnedDiagnosticTestReport;
    }

    @Test
    @Transactional
    void createDiagnosticTestReportWithExistingId() throws Exception {
        // Create the DiagnosticTestReport with an existing ID
        diagnosticTestReport.setId(1L);
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosticTestReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diagnosticTestReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiagnosticTestReports() throws Exception {
        // Initialize the database
        insertedDiagnosticTestReport = diagnosticTestReportRepository.saveAndFlush(diagnosticTestReport);

        // Get all the diagnosticTestReportList
        restDiagnosticTestReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosticTestReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].signedBy").value(hasItem(DEFAULT_SIGNED_BY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getDiagnosticTestReport() throws Exception {
        // Initialize the database
        insertedDiagnosticTestReport = diagnosticTestReportRepository.saveAndFlush(diagnosticTestReport);

        // Get the diagnosticTestReport
        restDiagnosticTestReportMockMvc
            .perform(get(ENTITY_API_URL_ID, diagnosticTestReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diagnosticTestReport.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.signedBy").value(DEFAULT_SIGNED_BY))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDiagnosticTestReport() throws Exception {
        // Get the diagnosticTestReport
        restDiagnosticTestReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDiagnosticTestReport() throws Exception {
        // Initialize the database
        insertedDiagnosticTestReport = diagnosticTestReportRepository.saveAndFlush(diagnosticTestReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diagnosticTestReport
        DiagnosticTestReport updatedDiagnosticTestReport = diagnosticTestReportRepository
            .findById(diagnosticTestReport.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDiagnosticTestReport are not directly saved in db
        em.detach(updatedDiagnosticTestReport);
        updatedDiagnosticTestReport
            .description(UPDATED_DESCRIPTION)
            .signedBy(UPDATED_SIGNED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(updatedDiagnosticTestReport);

        restDiagnosticTestReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diagnosticTestReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(diagnosticTestReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDiagnosticTestReportToMatchAllProperties(updatedDiagnosticTestReport);
    }

    @Test
    @Transactional
    void putNonExistingDiagnosticTestReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTestReport.setId(longCount.incrementAndGet());

        // Create the DiagnosticTestReport
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosticTestReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diagnosticTestReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(diagnosticTestReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiagnosticTestReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTestReport.setId(longCount.incrementAndGet());

        // Create the DiagnosticTestReport
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(diagnosticTestReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiagnosticTestReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTestReport.setId(longCount.incrementAndGet());

        // Create the DiagnosticTestReport
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(diagnosticTestReportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiagnosticTestReportWithPatch() throws Exception {
        // Initialize the database
        insertedDiagnosticTestReport = diagnosticTestReportRepository.saveAndFlush(diagnosticTestReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diagnosticTestReport using partial update
        DiagnosticTestReport partialUpdatedDiagnosticTestReport = new DiagnosticTestReport();
        partialUpdatedDiagnosticTestReport.setId(diagnosticTestReport.getId());

        partialUpdatedDiagnosticTestReport.description(UPDATED_DESCRIPTION).isActive(UPDATED_IS_ACTIVE).createdOn(UPDATED_CREATED_ON);

        restDiagnosticTestReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosticTestReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiagnosticTestReport))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosticTestReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiagnosticTestReportUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDiagnosticTestReport, diagnosticTestReport),
            getPersistedDiagnosticTestReport(diagnosticTestReport)
        );
    }

    @Test
    @Transactional
    void fullUpdateDiagnosticTestReportWithPatch() throws Exception {
        // Initialize the database
        insertedDiagnosticTestReport = diagnosticTestReportRepository.saveAndFlush(diagnosticTestReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the diagnosticTestReport using partial update
        DiagnosticTestReport partialUpdatedDiagnosticTestReport = new DiagnosticTestReport();
        partialUpdatedDiagnosticTestReport.setId(diagnosticTestReport.getId());

        partialUpdatedDiagnosticTestReport
            .description(UPDATED_DESCRIPTION)
            .signedBy(UPDATED_SIGNED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDiagnosticTestReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosticTestReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDiagnosticTestReport))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosticTestReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDiagnosticTestReportUpdatableFieldsEquals(
            partialUpdatedDiagnosticTestReport,
            getPersistedDiagnosticTestReport(partialUpdatedDiagnosticTestReport)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDiagnosticTestReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTestReport.setId(longCount.incrementAndGet());

        // Create the DiagnosticTestReport
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosticTestReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diagnosticTestReportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(diagnosticTestReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiagnosticTestReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTestReport.setId(longCount.incrementAndGet());

        // Create the DiagnosticTestReport
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(diagnosticTestReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiagnosticTestReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        diagnosticTestReport.setId(longCount.incrementAndGet());

        // Create the DiagnosticTestReport
        DiagnosticTestReportDTO diagnosticTestReportDTO = diagnosticTestReportMapper.toDto(diagnosticTestReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosticTestReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(diagnosticTestReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiagnosticTestReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiagnosticTestReport() throws Exception {
        // Initialize the database
        insertedDiagnosticTestReport = diagnosticTestReportRepository.saveAndFlush(diagnosticTestReport);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the diagnosticTestReport
        restDiagnosticTestReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, diagnosticTestReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return diagnosticTestReportRepository.count();
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

    protected DiagnosticTestReport getPersistedDiagnosticTestReport(DiagnosticTestReport diagnosticTestReport) {
        return diagnosticTestReportRepository.findById(diagnosticTestReport.getId()).orElseThrow();
    }

    protected void assertPersistedDiagnosticTestReportToMatchAllProperties(DiagnosticTestReport expectedDiagnosticTestReport) {
        assertDiagnosticTestReportAllPropertiesEquals(
            expectedDiagnosticTestReport,
            getPersistedDiagnosticTestReport(expectedDiagnosticTestReport)
        );
    }

    protected void assertPersistedDiagnosticTestReportToMatchUpdatableProperties(DiagnosticTestReport expectedDiagnosticTestReport) {
        assertDiagnosticTestReportAllUpdatablePropertiesEquals(
            expectedDiagnosticTestReport,
            getPersistedDiagnosticTestReport(expectedDiagnosticTestReport)
        );
    }
}
