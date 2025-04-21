package com.farmer.be.web.rest;

import static com.farmer.be.domain.DoctorVisitAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.DoctorVisit;
import com.farmer.be.domain.enumeration.VisitStatus;
import com.farmer.be.repository.DoctorVisitRepository;
import com.farmer.be.service.dto.DoctorVisitDTO;
import com.farmer.be.service.mapper.DoctorVisitMapper;
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
 * Integration tests for the {@link DoctorVisitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoctorVisitResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final VisitStatus DEFAULT_STATUS = VisitStatus.Planned;
    private static final VisitStatus UPDATED_STATUS = VisitStatus.Visited;

    private static final LocalDate DEFAULT_VISIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VISIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_VISIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VISIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/doctor-visits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoctorVisitRepository doctorVisitRepository;

    @Autowired
    private DoctorVisitMapper doctorVisitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorVisitMockMvc;

    private DoctorVisit doctorVisit;

    private DoctorVisit insertedDoctorVisit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorVisit createEntity() {
        return new DoctorVisit()
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .visitDate(DEFAULT_VISIT_DATE)
            .visitTime(DEFAULT_VISIT_TIME)
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
    public static DoctorVisit createUpdatedEntity() {
        return new DoctorVisit()
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .visitDate(UPDATED_VISIT_DATE)
            .visitTime(UPDATED_VISIT_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        doctorVisit = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDoctorVisit != null) {
            doctorVisitRepository.delete(insertedDoctorVisit);
            insertedDoctorVisit = null;
        }
    }

    @Test
    @Transactional
    void createDoctorVisit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);
        var returnedDoctorVisitDTO = om.readValue(
            restDoctorVisitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctorVisitDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DoctorVisitDTO.class
        );

        // Validate the DoctorVisit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDoctorVisit = doctorVisitMapper.toEntity(returnedDoctorVisitDTO);
        assertDoctorVisitUpdatableFieldsEquals(returnedDoctorVisit, getPersistedDoctorVisit(returnedDoctorVisit));

        insertedDoctorVisit = returnedDoctorVisit;
    }

    @Test
    @Transactional
    void createDoctorVisitWithExistingId() throws Exception {
        // Create the DoctorVisit with an existing ID
        doctorVisit.setId(1L);
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctorVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoctorVisits() throws Exception {
        // Initialize the database
        insertedDoctorVisit = doctorVisitRepository.saveAndFlush(doctorVisit);

        // Get all the doctorVisitList
        restDoctorVisitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].visitDate").value(hasItem(DEFAULT_VISIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].visitTime").value(hasItem(DEFAULT_VISIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getDoctorVisit() throws Exception {
        // Initialize the database
        insertedDoctorVisit = doctorVisitRepository.saveAndFlush(doctorVisit);

        // Get the doctorVisit
        restDoctorVisitMockMvc
            .perform(get(ENTITY_API_URL_ID, doctorVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctorVisit.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.visitDate").value(DEFAULT_VISIT_DATE.toString()))
            .andExpect(jsonPath("$.visitTime").value(DEFAULT_VISIT_TIME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDoctorVisit() throws Exception {
        // Get the doctorVisit
        restDoctorVisitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctorVisit() throws Exception {
        // Initialize the database
        insertedDoctorVisit = doctorVisitRepository.saveAndFlush(doctorVisit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctorVisit
        DoctorVisit updatedDoctorVisit = doctorVisitRepository.findById(doctorVisit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoctorVisit are not directly saved in db
        em.detach(updatedDoctorVisit);
        updatedDoctorVisit
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .visitDate(UPDATED_VISIT_DATE)
            .visitTime(UPDATED_VISIT_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(updatedDoctorVisit);

        restDoctorVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorVisitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doctorVisitDTO))
            )
            .andExpect(status().isOk());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoctorVisitToMatchAllProperties(updatedDoctorVisit);
    }

    @Test
    @Transactional
    void putNonExistingDoctorVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisit.setId(longCount.incrementAndGet());

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorVisitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doctorVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctorVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisit.setId(longCount.incrementAndGet());

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doctorVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctorVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisit.setId(longCount.incrementAndGet());

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctorVisitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorVisitWithPatch() throws Exception {
        // Initialize the database
        insertedDoctorVisit = doctorVisitRepository.saveAndFlush(doctorVisit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctorVisit using partial update
        DoctorVisit partialUpdatedDoctorVisit = new DoctorVisit();
        partialUpdatedDoctorVisit.setId(doctorVisit.getId());

        partialUpdatedDoctorVisit.visitTime(UPDATED_VISIT_TIME);

        restDoctorVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoctorVisit))
            )
            .andExpect(status().isOk());

        // Validate the DoctorVisit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoctorVisitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDoctorVisit, doctorVisit),
            getPersistedDoctorVisit(doctorVisit)
        );
    }

    @Test
    @Transactional
    void fullUpdateDoctorVisitWithPatch() throws Exception {
        // Initialize the database
        insertedDoctorVisit = doctorVisitRepository.saveAndFlush(doctorVisit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctorVisit using partial update
        DoctorVisit partialUpdatedDoctorVisit = new DoctorVisit();
        partialUpdatedDoctorVisit.setId(doctorVisit.getId());

        partialUpdatedDoctorVisit
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .visitDate(UPDATED_VISIT_DATE)
            .visitTime(UPDATED_VISIT_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restDoctorVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctorVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoctorVisit))
            )
            .andExpect(status().isOk());

        // Validate the DoctorVisit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoctorVisitUpdatableFieldsEquals(partialUpdatedDoctorVisit, getPersistedDoctorVisit(partialUpdatedDoctorVisit));
    }

    @Test
    @Transactional
    void patchNonExistingDoctorVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisit.setId(longCount.incrementAndGet());

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctorVisitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doctorVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctorVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisit.setId(longCount.incrementAndGet());

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doctorVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctorVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctorVisit.setId(longCount.incrementAndGet());

        // Create the DoctorVisit
        DoctorVisitDTO doctorVisitDTO = doctorVisitMapper.toDto(doctorVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorVisitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doctorVisitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DoctorVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctorVisit() throws Exception {
        // Initialize the database
        insertedDoctorVisit = doctorVisitRepository.saveAndFlush(doctorVisit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doctorVisit
        restDoctorVisitMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctorVisit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doctorVisitRepository.count();
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

    protected DoctorVisit getPersistedDoctorVisit(DoctorVisit doctorVisit) {
        return doctorVisitRepository.findById(doctorVisit.getId()).orElseThrow();
    }

    protected void assertPersistedDoctorVisitToMatchAllProperties(DoctorVisit expectedDoctorVisit) {
        assertDoctorVisitAllPropertiesEquals(expectedDoctorVisit, getPersistedDoctorVisit(expectedDoctorVisit));
    }

    protected void assertPersistedDoctorVisitToMatchUpdatableProperties(DoctorVisit expectedDoctorVisit) {
        assertDoctorVisitAllUpdatablePropertiesEquals(expectedDoctorVisit, getPersistedDoctorVisit(expectedDoctorVisit));
    }
}
