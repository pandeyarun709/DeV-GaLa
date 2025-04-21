package com.farmer.be.web.rest;

import static com.farmer.be.domain.SlotAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Slot;
import com.farmer.be.domain.enumeration.Day;
import com.farmer.be.repository.SlotRepository;
import com.farmer.be.service.dto.SlotDTO;
import com.farmer.be.service.mapper.SlotMapper;
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
 * Integration tests for the {@link SlotResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SlotResourceIT {

    private static final Day DEFAULT_DAY = Day.Mon;
    private static final Day UPDATED_DAY = Day.Tue;

    private static final Long DEFAULT_START_TIME_HOUR = 1L;
    private static final Long UPDATED_START_TIME_HOUR = 2L;

    private static final Long DEFAULT_START_TIME_MIN = 1L;
    private static final Long UPDATED_START_TIME_MIN = 2L;

    private static final Long DEFAULT_END_TIME_HOUR = 1L;
    private static final Long UPDATED_END_TIME_HOUR = 2L;

    private static final Long DEFAULT_END_TIME_MIN = 1L;
    private static final Long UPDATED_END_TIME_MIN = 2L;

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

    private static final String ENTITY_API_URL = "/api/slots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private SlotMapper slotMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSlotMockMvc;

    private Slot slot;

    private Slot insertedSlot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Slot createEntity() {
        return new Slot()
            .day(DEFAULT_DAY)
            .startTimeHour(DEFAULT_START_TIME_HOUR)
            .startTimeMin(DEFAULT_START_TIME_MIN)
            .endTimeHour(DEFAULT_END_TIME_HOUR)
            .endTimeMin(DEFAULT_END_TIME_MIN)
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
    public static Slot createUpdatedEntity() {
        return new Slot()
            .day(UPDATED_DAY)
            .startTimeHour(UPDATED_START_TIME_HOUR)
            .startTimeMin(UPDATED_START_TIME_MIN)
            .endTimeHour(UPDATED_END_TIME_HOUR)
            .endTimeMin(UPDATED_END_TIME_MIN)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        slot = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSlot != null) {
            slotRepository.delete(insertedSlot);
            insertedSlot = null;
        }
    }

    @Test
    @Transactional
    void createSlot() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Slot
        SlotDTO slotDTO = slotMapper.toDto(slot);
        var returnedSlotDTO = om.readValue(
            restSlotMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(slotDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SlotDTO.class
        );

        // Validate the Slot in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSlot = slotMapper.toEntity(returnedSlotDTO);
        assertSlotUpdatableFieldsEquals(returnedSlot, getPersistedSlot(returnedSlot));

        insertedSlot = returnedSlot;
    }

    @Test
    @Transactional
    void createSlotWithExistingId() throws Exception {
        // Create the Slot with an existing ID
        slot.setId(1L);
        SlotDTO slotDTO = slotMapper.toDto(slot);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSlotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(slotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSlots() throws Exception {
        // Initialize the database
        insertedSlot = slotRepository.saveAndFlush(slot);

        // Get all the slotList
        restSlotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slot.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].startTimeHour").value(hasItem(DEFAULT_START_TIME_HOUR.intValue())))
            .andExpect(jsonPath("$.[*].startTimeMin").value(hasItem(DEFAULT_START_TIME_MIN.intValue())))
            .andExpect(jsonPath("$.[*].endTimeHour").value(hasItem(DEFAULT_END_TIME_HOUR.intValue())))
            .andExpect(jsonPath("$.[*].endTimeMin").value(hasItem(DEFAULT_END_TIME_MIN.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getSlot() throws Exception {
        // Initialize the database
        insertedSlot = slotRepository.saveAndFlush(slot);

        // Get the slot
        restSlotMockMvc
            .perform(get(ENTITY_API_URL_ID, slot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(slot.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.startTimeHour").value(DEFAULT_START_TIME_HOUR.intValue()))
            .andExpect(jsonPath("$.startTimeMin").value(DEFAULT_START_TIME_MIN.intValue()))
            .andExpect(jsonPath("$.endTimeHour").value(DEFAULT_END_TIME_HOUR.intValue()))
            .andExpect(jsonPath("$.endTimeMin").value(DEFAULT_END_TIME_MIN.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSlot() throws Exception {
        // Get the slot
        restSlotMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSlot() throws Exception {
        // Initialize the database
        insertedSlot = slotRepository.saveAndFlush(slot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the slot
        Slot updatedSlot = slotRepository.findById(slot.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSlot are not directly saved in db
        em.detach(updatedSlot);
        updatedSlot
            .day(UPDATED_DAY)
            .startTimeHour(UPDATED_START_TIME_HOUR)
            .startTimeMin(UPDATED_START_TIME_MIN)
            .endTimeHour(UPDATED_END_TIME_HOUR)
            .endTimeMin(UPDATED_END_TIME_MIN)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SlotDTO slotDTO = slotMapper.toDto(updatedSlot);

        restSlotMockMvc
            .perform(put(ENTITY_API_URL_ID, slotDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(slotDTO)))
            .andExpect(status().isOk());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSlotToMatchAllProperties(updatedSlot);
    }

    @Test
    @Transactional
    void putNonExistingSlot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        slot.setId(longCount.incrementAndGet());

        // Create the Slot
        SlotDTO slotDTO = slotMapper.toDto(slot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlotMockMvc
            .perform(put(ENTITY_API_URL_ID, slotDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(slotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSlot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        slot.setId(longCount.incrementAndGet());

        // Create the Slot
        SlotDTO slotDTO = slotMapper.toDto(slot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(slotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSlot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        slot.setId(longCount.incrementAndGet());

        // Create the Slot
        SlotDTO slotDTO = slotMapper.toDto(slot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlotMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(slotDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSlotWithPatch() throws Exception {
        // Initialize the database
        insertedSlot = slotRepository.saveAndFlush(slot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the slot using partial update
        Slot partialUpdatedSlot = new Slot();
        partialUpdatedSlot.setId(slot.getId());

        partialUpdatedSlot
            .startTimeMin(UPDATED_START_TIME_MIN)
            .endTimeHour(UPDATED_END_TIME_HOUR)
            .endTimeMin(UPDATED_END_TIME_MIN)
            .createdBy(UPDATED_CREATED_BY);

        restSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSlot.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSlot))
            )
            .andExpect(status().isOk());

        // Validate the Slot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSlotUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSlot, slot), getPersistedSlot(slot));
    }

    @Test
    @Transactional
    void fullUpdateSlotWithPatch() throws Exception {
        // Initialize the database
        insertedSlot = slotRepository.saveAndFlush(slot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the slot using partial update
        Slot partialUpdatedSlot = new Slot();
        partialUpdatedSlot.setId(slot.getId());

        partialUpdatedSlot
            .day(UPDATED_DAY)
            .startTimeHour(UPDATED_START_TIME_HOUR)
            .startTimeMin(UPDATED_START_TIME_MIN)
            .endTimeHour(UPDATED_END_TIME_HOUR)
            .endTimeMin(UPDATED_END_TIME_MIN)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSlot.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSlot))
            )
            .andExpect(status().isOk());

        // Validate the Slot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSlotUpdatableFieldsEquals(partialUpdatedSlot, getPersistedSlot(partialUpdatedSlot));
    }

    @Test
    @Transactional
    void patchNonExistingSlot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        slot.setId(longCount.incrementAndGet());

        // Create the Slot
        SlotDTO slotDTO = slotMapper.toDto(slot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, slotDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(slotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSlot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        slot.setId(longCount.incrementAndGet());

        // Create the Slot
        SlotDTO slotDTO = slotMapper.toDto(slot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(slotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSlot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        slot.setId(longCount.incrementAndGet());

        // Create the Slot
        SlotDTO slotDTO = slotMapper.toDto(slot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSlotMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(slotDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Slot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSlot() throws Exception {
        // Initialize the database
        insertedSlot = slotRepository.saveAndFlush(slot);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the slot
        restSlotMockMvc
            .perform(delete(ENTITY_API_URL_ID, slot.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return slotRepository.count();
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

    protected Slot getPersistedSlot(Slot slot) {
        return slotRepository.findById(slot.getId()).orElseThrow();
    }

    protected void assertPersistedSlotToMatchAllProperties(Slot expectedSlot) {
        assertSlotAllPropertiesEquals(expectedSlot, getPersistedSlot(expectedSlot));
    }

    protected void assertPersistedSlotToMatchUpdatableProperties(Slot expectedSlot) {
        assertSlotAllUpdatablePropertiesEquals(expectedSlot, getPersistedSlot(expectedSlot));
    }
}
