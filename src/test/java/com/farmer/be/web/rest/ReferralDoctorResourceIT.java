package com.farmer.be.web.rest;

import static com.farmer.be.domain.ReferralDoctorAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.ReferralDoctor;
import com.farmer.be.repository.ReferralDoctorRepository;
import com.farmer.be.service.dto.ReferralDoctorDTO;
import com.farmer.be.service.mapper.ReferralDoctorMapper;
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
 * Integration tests for the {@link ReferralDoctorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReferralDoctorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/referral-doctors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReferralDoctorRepository referralDoctorRepository;

    @Autowired
    private ReferralDoctorMapper referralDoctorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReferralDoctorMockMvc;

    private ReferralDoctor referralDoctor;

    private ReferralDoctor insertedReferralDoctor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferralDoctor createEntity() {
        return new ReferralDoctor()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .registrationNo(DEFAULT_REGISTRATION_NO)
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
    public static ReferralDoctor createUpdatedEntity() {
        return new ReferralDoctor()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        referralDoctor = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedReferralDoctor != null) {
            referralDoctorRepository.delete(insertedReferralDoctor);
            insertedReferralDoctor = null;
        }
    }

    @Test
    @Transactional
    void createReferralDoctor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ReferralDoctor
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);
        var returnedReferralDoctorDTO = om.readValue(
            restReferralDoctorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(referralDoctorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ReferralDoctorDTO.class
        );

        // Validate the ReferralDoctor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedReferralDoctor = referralDoctorMapper.toEntity(returnedReferralDoctorDTO);
        assertReferralDoctorUpdatableFieldsEquals(returnedReferralDoctor, getPersistedReferralDoctor(returnedReferralDoctor));

        insertedReferralDoctor = returnedReferralDoctor;
    }

    @Test
    @Transactional
    void createReferralDoctorWithExistingId() throws Exception {
        // Create the ReferralDoctor with an existing ID
        referralDoctor.setId(1L);
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferralDoctorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(referralDoctorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReferralDoctors() throws Exception {
        // Initialize the database
        insertedReferralDoctor = referralDoctorRepository.saveAndFlush(referralDoctor);

        // Get all the referralDoctorList
        restReferralDoctorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referralDoctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].registrationNo").value(hasItem(DEFAULT_REGISTRATION_NO)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getReferralDoctor() throws Exception {
        // Initialize the database
        insertedReferralDoctor = referralDoctorRepository.saveAndFlush(referralDoctor);

        // Get the referralDoctor
        restReferralDoctorMockMvc
            .perform(get(ENTITY_API_URL_ID, referralDoctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(referralDoctor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.registrationNo").value(DEFAULT_REGISTRATION_NO))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReferralDoctor() throws Exception {
        // Get the referralDoctor
        restReferralDoctorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReferralDoctor() throws Exception {
        // Initialize the database
        insertedReferralDoctor = referralDoctorRepository.saveAndFlush(referralDoctor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the referralDoctor
        ReferralDoctor updatedReferralDoctor = referralDoctorRepository.findById(referralDoctor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReferralDoctor are not directly saved in db
        em.detach(updatedReferralDoctor);
        updatedReferralDoctor
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(updatedReferralDoctor);

        restReferralDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, referralDoctorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(referralDoctorDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReferralDoctorToMatchAllProperties(updatedReferralDoctor);
    }

    @Test
    @Transactional
    void putNonExistingReferralDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        referralDoctor.setId(longCount.incrementAndGet());

        // Create the ReferralDoctor
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferralDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, referralDoctorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(referralDoctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReferralDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        referralDoctor.setId(longCount.incrementAndGet());

        // Create the ReferralDoctor
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferralDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(referralDoctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReferralDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        referralDoctor.setId(longCount.incrementAndGet());

        // Create the ReferralDoctor
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferralDoctorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(referralDoctorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReferralDoctorWithPatch() throws Exception {
        // Initialize the database
        insertedReferralDoctor = referralDoctorRepository.saveAndFlush(referralDoctor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the referralDoctor using partial update
        ReferralDoctor partialUpdatedReferralDoctor = new ReferralDoctor();
        partialUpdatedReferralDoctor.setId(referralDoctor.getId());

        partialUpdatedReferralDoctor
            .phone(UPDATED_PHONE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restReferralDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReferralDoctor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReferralDoctor))
            )
            .andExpect(status().isOk());

        // Validate the ReferralDoctor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReferralDoctorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReferralDoctor, referralDoctor),
            getPersistedReferralDoctor(referralDoctor)
        );
    }

    @Test
    @Transactional
    void fullUpdateReferralDoctorWithPatch() throws Exception {
        // Initialize the database
        insertedReferralDoctor = referralDoctorRepository.saveAndFlush(referralDoctor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the referralDoctor using partial update
        ReferralDoctor partialUpdatedReferralDoctor = new ReferralDoctor();
        partialUpdatedReferralDoctor.setId(referralDoctor.getId());

        partialUpdatedReferralDoctor
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restReferralDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReferralDoctor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReferralDoctor))
            )
            .andExpect(status().isOk());

        // Validate the ReferralDoctor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReferralDoctorUpdatableFieldsEquals(partialUpdatedReferralDoctor, getPersistedReferralDoctor(partialUpdatedReferralDoctor));
    }

    @Test
    @Transactional
    void patchNonExistingReferralDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        referralDoctor.setId(longCount.incrementAndGet());

        // Create the ReferralDoctor
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferralDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, referralDoctorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(referralDoctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReferralDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        referralDoctor.setId(longCount.incrementAndGet());

        // Create the ReferralDoctor
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferralDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(referralDoctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReferralDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        referralDoctor.setId(longCount.incrementAndGet());

        // Create the ReferralDoctor
        ReferralDoctorDTO referralDoctorDTO = referralDoctorMapper.toDto(referralDoctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferralDoctorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(referralDoctorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReferralDoctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReferralDoctor() throws Exception {
        // Initialize the database
        insertedReferralDoctor = referralDoctorRepository.saveAndFlush(referralDoctor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the referralDoctor
        restReferralDoctorMockMvc
            .perform(delete(ENTITY_API_URL_ID, referralDoctor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return referralDoctorRepository.count();
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

    protected ReferralDoctor getPersistedReferralDoctor(ReferralDoctor referralDoctor) {
        return referralDoctorRepository.findById(referralDoctor.getId()).orElseThrow();
    }

    protected void assertPersistedReferralDoctorToMatchAllProperties(ReferralDoctor expectedReferralDoctor) {
        assertReferralDoctorAllPropertiesEquals(expectedReferralDoctor, getPersistedReferralDoctor(expectedReferralDoctor));
    }

    protected void assertPersistedReferralDoctorToMatchUpdatableProperties(ReferralDoctor expectedReferralDoctor) {
        assertReferralDoctorAllUpdatablePropertiesEquals(expectedReferralDoctor, getPersistedReferralDoctor(expectedReferralDoctor));
    }
}
