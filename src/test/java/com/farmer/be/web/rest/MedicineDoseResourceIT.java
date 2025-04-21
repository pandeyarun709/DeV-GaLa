package com.farmer.be.web.rest;

import static com.farmer.be.domain.MedicineDoseAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.MedicineDose;
import com.farmer.be.repository.MedicineDoseRepository;
import com.farmer.be.service.dto.MedicineDoseDTO;
import com.farmer.be.service.mapper.MedicineDoseMapper;
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
 * Integration tests for the {@link MedicineDoseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicineDoseResourceIT {

    private static final String DEFAULT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENCY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/medicine-doses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MedicineDoseRepository medicineDoseRepository;

    @Autowired
    private MedicineDoseMapper medicineDoseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicineDoseMockMvc;

    private MedicineDose medicineDose;

    private MedicineDose insertedMedicineDose;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicineDose createEntity() {
        return new MedicineDose()
            .frequency(DEFAULT_FREQUENCY)
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
    public static MedicineDose createUpdatedEntity() {
        return new MedicineDose()
            .frequency(UPDATED_FREQUENCY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        medicineDose = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMedicineDose != null) {
            medicineDoseRepository.delete(insertedMedicineDose);
            insertedMedicineDose = null;
        }
    }

    @Test
    @Transactional
    void createMedicineDose() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MedicineDose
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);
        var returnedMedicineDoseDTO = om.readValue(
            restMedicineDoseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineDoseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MedicineDoseDTO.class
        );

        // Validate the MedicineDose in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMedicineDose = medicineDoseMapper.toEntity(returnedMedicineDoseDTO);
        assertMedicineDoseUpdatableFieldsEquals(returnedMedicineDose, getPersistedMedicineDose(returnedMedicineDose));

        insertedMedicineDose = returnedMedicineDose;
    }

    @Test
    @Transactional
    void createMedicineDoseWithExistingId() throws Exception {
        // Create the MedicineDose with an existing ID
        medicineDose.setId(1L);
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineDoseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineDoseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedicineDoses() throws Exception {
        // Initialize the database
        insertedMedicineDose = medicineDoseRepository.saveAndFlush(medicineDose);

        // Get all the medicineDoseList
        restMedicineDoseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicineDose.getId().intValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getMedicineDose() throws Exception {
        // Initialize the database
        insertedMedicineDose = medicineDoseRepository.saveAndFlush(medicineDose);

        // Get the medicineDose
        restMedicineDoseMockMvc
            .perform(get(ENTITY_API_URL_ID, medicineDose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicineDose.getId().intValue()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMedicineDose() throws Exception {
        // Get the medicineDose
        restMedicineDoseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedicineDose() throws Exception {
        // Initialize the database
        insertedMedicineDose = medicineDoseRepository.saveAndFlush(medicineDose);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicineDose
        MedicineDose updatedMedicineDose = medicineDoseRepository.findById(medicineDose.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMedicineDose are not directly saved in db
        em.detach(updatedMedicineDose);
        updatedMedicineDose
            .frequency(UPDATED_FREQUENCY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(updatedMedicineDose);

        restMedicineDoseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicineDoseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineDoseDTO))
            )
            .andExpect(status().isOk());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMedicineDoseToMatchAllProperties(updatedMedicineDose);
    }

    @Test
    @Transactional
    void putNonExistingMedicineDose() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineDose.setId(longCount.incrementAndGet());

        // Create the MedicineDose
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineDoseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicineDoseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineDoseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicineDose() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineDose.setId(longCount.incrementAndGet());

        // Create the MedicineDose
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineDoseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineDoseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicineDose() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineDose.setId(longCount.incrementAndGet());

        // Create the MedicineDose
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineDoseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineDoseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicineDoseWithPatch() throws Exception {
        // Initialize the database
        insertedMedicineDose = medicineDoseRepository.saveAndFlush(medicineDose);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicineDose using partial update
        MedicineDose partialUpdatedMedicineDose = new MedicineDose();
        partialUpdatedMedicineDose.setId(medicineDose.getId());

        partialUpdatedMedicineDose.frequency(UPDATED_FREQUENCY).isActive(UPDATED_IS_ACTIVE).updatedOn(UPDATED_UPDATED_ON);

        restMedicineDoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicineDose.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedicineDose))
            )
            .andExpect(status().isOk());

        // Validate the MedicineDose in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicineDoseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMedicineDose, medicineDose),
            getPersistedMedicineDose(medicineDose)
        );
    }

    @Test
    @Transactional
    void fullUpdateMedicineDoseWithPatch() throws Exception {
        // Initialize the database
        insertedMedicineDose = medicineDoseRepository.saveAndFlush(medicineDose);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicineDose using partial update
        MedicineDose partialUpdatedMedicineDose = new MedicineDose();
        partialUpdatedMedicineDose.setId(medicineDose.getId());

        partialUpdatedMedicineDose
            .frequency(UPDATED_FREQUENCY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restMedicineDoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicineDose.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedicineDose))
            )
            .andExpect(status().isOk());

        // Validate the MedicineDose in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicineDoseUpdatableFieldsEquals(partialUpdatedMedicineDose, getPersistedMedicineDose(partialUpdatedMedicineDose));
    }

    @Test
    @Transactional
    void patchNonExistingMedicineDose() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineDose.setId(longCount.incrementAndGet());

        // Create the MedicineDose
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineDoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicineDoseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medicineDoseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicineDose() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineDose.setId(longCount.incrementAndGet());

        // Create the MedicineDose
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineDoseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medicineDoseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicineDose() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicineDose.setId(longCount.incrementAndGet());

        // Create the MedicineDose
        MedicineDoseDTO medicineDoseDTO = medicineDoseMapper.toDto(medicineDose);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineDoseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(medicineDoseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicineDose in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicineDose() throws Exception {
        // Initialize the database
        insertedMedicineDose = medicineDoseRepository.saveAndFlush(medicineDose);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the medicineDose
        restMedicineDoseMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicineDose.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return medicineDoseRepository.count();
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

    protected MedicineDose getPersistedMedicineDose(MedicineDose medicineDose) {
        return medicineDoseRepository.findById(medicineDose.getId()).orElseThrow();
    }

    protected void assertPersistedMedicineDoseToMatchAllProperties(MedicineDose expectedMedicineDose) {
        assertMedicineDoseAllPropertiesEquals(expectedMedicineDose, getPersistedMedicineDose(expectedMedicineDose));
    }

    protected void assertPersistedMedicineDoseToMatchUpdatableProperties(MedicineDose expectedMedicineDose) {
        assertMedicineDoseAllUpdatablePropertiesEquals(expectedMedicineDose, getPersistedMedicineDose(expectedMedicineDose));
    }
}
