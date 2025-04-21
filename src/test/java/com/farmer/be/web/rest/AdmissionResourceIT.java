package com.farmer.be.web.rest;

import static com.farmer.be.domain.AdmissionAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Admission;
import com.farmer.be.domain.enumeration.AdmissionStatus;
import com.farmer.be.domain.enumeration.DischargeStatus;
import com.farmer.be.domain.enumeration.PaymentStatus;
import com.farmer.be.repository.AdmissionRepository;
import com.farmer.be.service.AdmissionService;
import com.farmer.be.service.dto.AdmissionDTO;
import com.farmer.be.service.mapper.AdmissionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdmissionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdmissionResourceIT {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final AdmissionStatus DEFAULT_ADMISSION_STATUS = AdmissionStatus.Accidental;
    private static final AdmissionStatus UPDATED_ADMISSION_STATUS = AdmissionStatus.PoliceVerificationNeeded;

    private static final DischargeStatus DEFAULT_DISCHARGE_STATUS = DischargeStatus.NotDischarged;
    private static final DischargeStatus UPDATED_DISCHARGE_STATUS = DischargeStatus.Expired;

    private static final Instant DEFAULT_ADMISSION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADMISSION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DISCHARGE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCHARGE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.Paid;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.Pending;

    private static final Double DEFAULT_TOTAL_BILL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_BILL_AMOUNT = 2D;

    private static final Double DEFAULT_INSURANCE_COVERED_AMOUNT = 1D;
    private static final Double UPDATED_INSURANCE_COVERED_AMOUNT = 2D;

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

    private static final String ENTITY_API_URL = "/api/admissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdmissionRepository admissionRepository;

    @Mock
    private AdmissionRepository admissionRepositoryMock;

    @Autowired
    private AdmissionMapper admissionMapper;

    @Mock
    private AdmissionService admissionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdmissionMockMvc;

    private Admission admission;

    private Admission insertedAdmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admission createEntity() {
        return new Admission()
            .details(DEFAULT_DETAILS)
            .admissionStatus(DEFAULT_ADMISSION_STATUS)
            .dischargeStatus(DEFAULT_DISCHARGE_STATUS)
            .admissionTime(DEFAULT_ADMISSION_TIME)
            .dischargeTime(DEFAULT_DISCHARGE_TIME)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .totalBillAmount(DEFAULT_TOTAL_BILL_AMOUNT)
            .insuranceCoveredAmount(DEFAULT_INSURANCE_COVERED_AMOUNT)
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
    public static Admission createUpdatedEntity() {
        return new Admission()
            .details(UPDATED_DETAILS)
            .admissionStatus(UPDATED_ADMISSION_STATUS)
            .dischargeStatus(UPDATED_DISCHARGE_STATUS)
            .admissionTime(UPDATED_ADMISSION_TIME)
            .dischargeTime(UPDATED_DISCHARGE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .totalBillAmount(UPDATED_TOTAL_BILL_AMOUNT)
            .insuranceCoveredAmount(UPDATED_INSURANCE_COVERED_AMOUNT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        admission = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAdmission != null) {
            admissionRepository.delete(insertedAdmission);
            insertedAdmission = null;
        }
    }

    @Test
    @Transactional
    void createAdmission() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Admission
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);
        var returnedAdmissionDTO = om.readValue(
            restAdmissionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(admissionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdmissionDTO.class
        );

        // Validate the Admission in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAdmission = admissionMapper.toEntity(returnedAdmissionDTO);
        assertAdmissionUpdatableFieldsEquals(returnedAdmission, getPersistedAdmission(returnedAdmission));

        insertedAdmission = returnedAdmission;
    }

    @Test
    @Transactional
    void createAdmissionWithExistingId() throws Exception {
        // Create the Admission with an existing ID
        admission.setId(1L);
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdmissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(admissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdmissions() throws Exception {
        // Initialize the database
        insertedAdmission = admissionRepository.saveAndFlush(admission);

        // Get all the admissionList
        restAdmissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(admission.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].admissionStatus").value(hasItem(DEFAULT_ADMISSION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dischargeStatus").value(hasItem(DEFAULT_DISCHARGE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].admissionTime").value(hasItem(DEFAULT_ADMISSION_TIME.toString())))
            .andExpect(jsonPath("$.[*].dischargeTime").value(hasItem(DEFAULT_DISCHARGE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalBillAmount").value(hasItem(DEFAULT_TOTAL_BILL_AMOUNT)))
            .andExpect(jsonPath("$.[*].insuranceCoveredAmount").value(hasItem(DEFAULT_INSURANCE_COVERED_AMOUNT)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdmissionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(admissionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdmissionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(admissionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdmissionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(admissionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdmissionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(admissionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAdmission() throws Exception {
        // Initialize the database
        insertedAdmission = admissionRepository.saveAndFlush(admission);

        // Get the admission
        restAdmissionMockMvc
            .perform(get(ENTITY_API_URL_ID, admission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(admission.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.admissionStatus").value(DEFAULT_ADMISSION_STATUS.toString()))
            .andExpect(jsonPath("$.dischargeStatus").value(DEFAULT_DISCHARGE_STATUS.toString()))
            .andExpect(jsonPath("$.admissionTime").value(DEFAULT_ADMISSION_TIME.toString()))
            .andExpect(jsonPath("$.dischargeTime").value(DEFAULT_DISCHARGE_TIME.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.totalBillAmount").value(DEFAULT_TOTAL_BILL_AMOUNT))
            .andExpect(jsonPath("$.insuranceCoveredAmount").value(DEFAULT_INSURANCE_COVERED_AMOUNT))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdmission() throws Exception {
        // Get the admission
        restAdmissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdmission() throws Exception {
        // Initialize the database
        insertedAdmission = admissionRepository.saveAndFlush(admission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the admission
        Admission updatedAdmission = admissionRepository.findById(admission.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdmission are not directly saved in db
        em.detach(updatedAdmission);
        updatedAdmission
            .details(UPDATED_DETAILS)
            .admissionStatus(UPDATED_ADMISSION_STATUS)
            .dischargeStatus(UPDATED_DISCHARGE_STATUS)
            .admissionTime(UPDATED_ADMISSION_TIME)
            .dischargeTime(UPDATED_DISCHARGE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .totalBillAmount(UPDATED_TOTAL_BILL_AMOUNT)
            .insuranceCoveredAmount(UPDATED_INSURANCE_COVERED_AMOUNT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        AdmissionDTO admissionDTO = admissionMapper.toDto(updatedAdmission);

        restAdmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, admissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(admissionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdmissionToMatchAllProperties(updatedAdmission);
    }

    @Test
    @Transactional
    void putNonExistingAdmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        admission.setId(longCount.incrementAndGet());

        // Create the Admission
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, admissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(admissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        admission.setId(longCount.incrementAndGet());

        // Create the Admission
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(admissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        admission.setId(longCount.incrementAndGet());

        // Create the Admission
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdmissionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(admissionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdmissionWithPatch() throws Exception {
        // Initialize the database
        insertedAdmission = admissionRepository.saveAndFlush(admission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the admission using partial update
        Admission partialUpdatedAdmission = new Admission();
        partialUpdatedAdmission.setId(admission.getId());

        partialUpdatedAdmission
            .details(UPDATED_DETAILS)
            .dischargeStatus(UPDATED_DISCHARGE_STATUS)
            .admissionTime(UPDATED_ADMISSION_TIME)
            .totalBillAmount(UPDATED_TOTAL_BILL_AMOUNT)
            .insuranceCoveredAmount(UPDATED_INSURANCE_COVERED_AMOUNT)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restAdmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdmission))
            )
            .andExpect(status().isOk());

        // Validate the Admission in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdmissionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdmission, admission),
            getPersistedAdmission(admission)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdmissionWithPatch() throws Exception {
        // Initialize the database
        insertedAdmission = admissionRepository.saveAndFlush(admission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the admission using partial update
        Admission partialUpdatedAdmission = new Admission();
        partialUpdatedAdmission.setId(admission.getId());

        partialUpdatedAdmission
            .details(UPDATED_DETAILS)
            .admissionStatus(UPDATED_ADMISSION_STATUS)
            .dischargeStatus(UPDATED_DISCHARGE_STATUS)
            .admissionTime(UPDATED_ADMISSION_TIME)
            .dischargeTime(UPDATED_DISCHARGE_TIME)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .totalBillAmount(UPDATED_TOTAL_BILL_AMOUNT)
            .insuranceCoveredAmount(UPDATED_INSURANCE_COVERED_AMOUNT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restAdmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdmission))
            )
            .andExpect(status().isOk());

        // Validate the Admission in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdmissionUpdatableFieldsEquals(partialUpdatedAdmission, getPersistedAdmission(partialUpdatedAdmission));
    }

    @Test
    @Transactional
    void patchNonExistingAdmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        admission.setId(longCount.incrementAndGet());

        // Create the Admission
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, admissionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(admissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        admission.setId(longCount.incrementAndGet());

        // Create the Admission
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(admissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        admission.setId(longCount.incrementAndGet());

        // Create the Admission
        AdmissionDTO admissionDTO = admissionMapper.toDto(admission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdmissionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(admissionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Admission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdmission() throws Exception {
        // Initialize the database
        insertedAdmission = admissionRepository.saveAndFlush(admission);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the admission
        restAdmissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, admission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return admissionRepository.count();
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

    protected Admission getPersistedAdmission(Admission admission) {
        return admissionRepository.findById(admission.getId()).orElseThrow();
    }

    protected void assertPersistedAdmissionToMatchAllProperties(Admission expectedAdmission) {
        assertAdmissionAllPropertiesEquals(expectedAdmission, getPersistedAdmission(expectedAdmission));
    }

    protected void assertPersistedAdmissionToMatchUpdatableProperties(Admission expectedAdmission) {
        assertAdmissionAllUpdatablePropertiesEquals(expectedAdmission, getPersistedAdmission(expectedAdmission));
    }
}
