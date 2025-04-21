package com.farmer.be.web.rest;

import static com.farmer.be.domain.QualificationAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Qualification;
import com.farmer.be.repository.QualificationRepository;
import com.farmer.be.service.dto.QualificationDTO;
import com.farmer.be.service.mapper.QualificationMapper;
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
 * Integration tests for the {@link QualificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QualificationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/qualifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private QualificationMapper qualificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQualificationMockMvc;

    private Qualification qualification;

    private Qualification insertedQualification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Qualification createEntity() {
        return new Qualification()
            .name(DEFAULT_NAME)
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
    public static Qualification createUpdatedEntity() {
        return new Qualification()
            .name(UPDATED_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        qualification = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedQualification != null) {
            qualificationRepository.delete(insertedQualification);
            insertedQualification = null;
        }
    }

    @Test
    @Transactional
    void createQualification() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);
        var returnedQualificationDTO = om.readValue(
            restQualificationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qualificationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            QualificationDTO.class
        );

        // Validate the Qualification in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedQualification = qualificationMapper.toEntity(returnedQualificationDTO);
        assertQualificationUpdatableFieldsEquals(returnedQualification, getPersistedQualification(returnedQualification));

        insertedQualification = returnedQualification;
    }

    @Test
    @Transactional
    void createQualificationWithExistingId() throws Exception {
        // Create the Qualification with an existing ID
        qualification.setId(1L);
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qualificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQualifications() throws Exception {
        // Initialize the database
        insertedQualification = qualificationRepository.saveAndFlush(qualification);

        // Get all the qualificationList
        restQualificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualification.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getQualification() throws Exception {
        // Initialize the database
        insertedQualification = qualificationRepository.saveAndFlush(qualification);

        // Get the qualification
        restQualificationMockMvc
            .perform(get(ENTITY_API_URL_ID, qualification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qualification.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingQualification() throws Exception {
        // Get the qualification
        restQualificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQualification() throws Exception {
        // Initialize the database
        insertedQualification = qualificationRepository.saveAndFlush(qualification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qualification
        Qualification updatedQualification = qualificationRepository.findById(qualification.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedQualification are not directly saved in db
        em.detach(updatedQualification);
        updatedQualification
            .name(UPDATED_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        QualificationDTO qualificationDTO = qualificationMapper.toDto(updatedQualification);

        restQualificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qualificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qualificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedQualificationToMatchAllProperties(updatedQualification);
    }

    @Test
    @Transactional
    void putNonExistingQualification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qualification.setId(longCount.incrementAndGet());

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qualificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qualificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQualification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qualification.setId(longCount.incrementAndGet());

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(qualificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQualification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qualification.setId(longCount.incrementAndGet());

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(qualificationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQualificationWithPatch() throws Exception {
        // Initialize the database
        insertedQualification = qualificationRepository.saveAndFlush(qualification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qualification using partial update
        Qualification partialUpdatedQualification = new Qualification();
        partialUpdatedQualification.setId(qualification.getId());

        partialUpdatedQualification.createdBy(UPDATED_CREATED_BY).createdOn(UPDATED_CREATED_ON).updatedOn(UPDATED_UPDATED_ON);

        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualification.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQualification))
            )
            .andExpect(status().isOk());

        // Validate the Qualification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQualificationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedQualification, qualification),
            getPersistedQualification(qualification)
        );
    }

    @Test
    @Transactional
    void fullUpdateQualificationWithPatch() throws Exception {
        // Initialize the database
        insertedQualification = qualificationRepository.saveAndFlush(qualification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the qualification using partial update
        Qualification partialUpdatedQualification = new Qualification();
        partialUpdatedQualification.setId(qualification.getId());

        partialUpdatedQualification
            .name(UPDATED_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQualification.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedQualification))
            )
            .andExpect(status().isOk());

        // Validate the Qualification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertQualificationUpdatableFieldsEquals(partialUpdatedQualification, getPersistedQualification(partialUpdatedQualification));
    }

    @Test
    @Transactional
    void patchNonExistingQualification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qualification.setId(longCount.incrementAndGet());

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qualificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(qualificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQualification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qualification.setId(longCount.incrementAndGet());

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(qualificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQualification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        qualification.setId(longCount.incrementAndGet());

        // Create the Qualification
        QualificationDTO qualificationDTO = qualificationMapper.toDto(qualification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQualificationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(qualificationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Qualification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQualification() throws Exception {
        // Initialize the database
        insertedQualification = qualificationRepository.saveAndFlush(qualification);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the qualification
        restQualificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, qualification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return qualificationRepository.count();
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

    protected Qualification getPersistedQualification(Qualification qualification) {
        return qualificationRepository.findById(qualification.getId()).orElseThrow();
    }

    protected void assertPersistedQualificationToMatchAllProperties(Qualification expectedQualification) {
        assertQualificationAllPropertiesEquals(expectedQualification, getPersistedQualification(expectedQualification));
    }

    protected void assertPersistedQualificationToMatchUpdatableProperties(Qualification expectedQualification) {
        assertQualificationAllUpdatablePropertiesEquals(expectedQualification, getPersistedQualification(expectedQualification));
    }
}
