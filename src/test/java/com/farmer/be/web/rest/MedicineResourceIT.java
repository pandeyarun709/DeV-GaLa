package com.farmer.be.web.rest;

import static com.farmer.be.domain.MedicineAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Medicine;
import com.farmer.be.domain.enumeration.MedicineType;
import com.farmer.be.repository.MedicineRepository;
import com.farmer.be.service.dto.MedicineDTO;
import com.farmer.be.service.mapper.MedicineMapper;
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
 * Integration tests for the {@link MedicineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENERIC_MOLECULE = "AAAAAAAAAA";
    private static final String UPDATED_GENERIC_MOLECULE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PRESCRIPTION_NEEDED = false;
    private static final Boolean UPDATED_IS_PRESCRIPTION_NEEDED = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_MRP = 1D;
    private static final Double UPDATED_MRP = 2D;

    private static final Boolean DEFAULT_IS_INSURANCE_COVERED = false;
    private static final Boolean UPDATED_IS_INSURANCE_COVERED = true;

    private static final MedicineType DEFAULT_TYPE = MedicineType.Tablet;
    private static final MedicineType UPDATED_TYPE = MedicineType.Capsul;

    private static final Boolean DEFAULT_IS_CONSUMABLE = false;
    private static final Boolean UPDATED_IS_CONSUMABLE = true;

    private static final Boolean DEFAULT_IS_RETURNABLE = false;
    private static final Boolean UPDATED_IS_RETURNABLE = true;

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

    private static final String ENTITY_API_URL = "/api/medicines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineMapper medicineMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicineMockMvc;

    private Medicine medicine;

    private Medicine insertedMedicine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicine createEntity() {
        return new Medicine()
            .name(DEFAULT_NAME)
            .genericMolecule(DEFAULT_GENERIC_MOLECULE)
            .isPrescriptionNeeded(DEFAULT_IS_PRESCRIPTION_NEEDED)
            .description(DEFAULT_DESCRIPTION)
            .mrp(DEFAULT_MRP)
            .isInsuranceCovered(DEFAULT_IS_INSURANCE_COVERED)
            .type(DEFAULT_TYPE)
            .isConsumable(DEFAULT_IS_CONSUMABLE)
            .isReturnable(DEFAULT_IS_RETURNABLE)
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
    public static Medicine createUpdatedEntity() {
        return new Medicine()
            .name(UPDATED_NAME)
            .genericMolecule(UPDATED_GENERIC_MOLECULE)
            .isPrescriptionNeeded(UPDATED_IS_PRESCRIPTION_NEEDED)
            .description(UPDATED_DESCRIPTION)
            .mrp(UPDATED_MRP)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .type(UPDATED_TYPE)
            .isConsumable(UPDATED_IS_CONSUMABLE)
            .isReturnable(UPDATED_IS_RETURNABLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
    }

    @BeforeEach
    void initTest() {
        medicine = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMedicine != null) {
            medicineRepository.delete(insertedMedicine);
            insertedMedicine = null;
        }
    }

    @Test
    @Transactional
    void createMedicine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);
        var returnedMedicineDTO = om.readValue(
            restMedicineMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MedicineDTO.class
        );

        // Validate the Medicine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMedicine = medicineMapper.toEntity(returnedMedicineDTO);
        assertMedicineUpdatableFieldsEquals(returnedMedicine, getPersistedMedicine(returnedMedicine));

        insertedMedicine = returnedMedicine;
    }

    @Test
    @Transactional
    void createMedicineWithExistingId() throws Exception {
        // Create the Medicine with an existing ID
        medicine.setId(1L);
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedicines() throws Exception {
        // Initialize the database
        insertedMedicine = medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList
        restMedicineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].genericMolecule").value(hasItem(DEFAULT_GENERIC_MOLECULE)))
            .andExpect(jsonPath("$.[*].isPrescriptionNeeded").value(hasItem(DEFAULT_IS_PRESCRIPTION_NEEDED)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mrp").value(hasItem(DEFAULT_MRP)))
            .andExpect(jsonPath("$.[*].isInsuranceCovered").value(hasItem(DEFAULT_IS_INSURANCE_COVERED)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isConsumable").value(hasItem(DEFAULT_IS_CONSUMABLE)))
            .andExpect(jsonPath("$.[*].isReturnable").value(hasItem(DEFAULT_IS_RETURNABLE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    void getMedicine() throws Exception {
        // Initialize the database
        insertedMedicine = medicineRepository.saveAndFlush(medicine);

        // Get the medicine
        restMedicineMockMvc
            .perform(get(ENTITY_API_URL_ID, medicine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.genericMolecule").value(DEFAULT_GENERIC_MOLECULE))
            .andExpect(jsonPath("$.isPrescriptionNeeded").value(DEFAULT_IS_PRESCRIPTION_NEEDED))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.mrp").value(DEFAULT_MRP))
            .andExpect(jsonPath("$.isInsuranceCovered").value(DEFAULT_IS_INSURANCE_COVERED))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.isConsumable").value(DEFAULT_IS_CONSUMABLE))
            .andExpect(jsonPath("$.isReturnable").value(DEFAULT_IS_RETURNABLE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMedicine() throws Exception {
        // Get the medicine
        restMedicineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedicine() throws Exception {
        // Initialize the database
        insertedMedicine = medicineRepository.saveAndFlush(medicine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicine
        Medicine updatedMedicine = medicineRepository.findById(medicine.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMedicine are not directly saved in db
        em.detach(updatedMedicine);
        updatedMedicine
            .name(UPDATED_NAME)
            .genericMolecule(UPDATED_GENERIC_MOLECULE)
            .isPrescriptionNeeded(UPDATED_IS_PRESCRIPTION_NEEDED)
            .description(UPDATED_DESCRIPTION)
            .mrp(UPDATED_MRP)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .type(UPDATED_TYPE)
            .isConsumable(UPDATED_IS_CONSUMABLE)
            .isReturnable(UPDATED_IS_RETURNABLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        MedicineDTO medicineDTO = medicineMapper.toDto(updatedMedicine);

        restMedicineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicineDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineDTO))
            )
            .andExpect(status().isOk());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMedicineToMatchAllProperties(updatedMedicine);
    }

    @Test
    @Transactional
    void putNonExistingMedicine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicine.setId(longCount.incrementAndGet());

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicineDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicine.setId(longCount.incrementAndGet());

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(medicineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicine.setId(longCount.incrementAndGet());

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(medicineDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicineWithPatch() throws Exception {
        // Initialize the database
        insertedMedicine = medicineRepository.saveAndFlush(medicine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicine using partial update
        Medicine partialUpdatedMedicine = new Medicine();
        partialUpdatedMedicine.setId(medicine.getId());

        partialUpdatedMedicine
            .name(UPDATED_NAME)
            .isPrescriptionNeeded(UPDATED_IS_PRESCRIPTION_NEEDED)
            .description(UPDATED_DESCRIPTION)
            .mrp(UPDATED_MRP)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedicine))
            )
            .andExpect(status().isOk());

        // Validate the Medicine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicineUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMedicine, medicine), getPersistedMedicine(medicine));
    }

    @Test
    @Transactional
    void fullUpdateMedicineWithPatch() throws Exception {
        // Initialize the database
        insertedMedicine = medicineRepository.saveAndFlush(medicine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the medicine using partial update
        Medicine partialUpdatedMedicine = new Medicine();
        partialUpdatedMedicine.setId(medicine.getId());

        partialUpdatedMedicine
            .name(UPDATED_NAME)
            .genericMolecule(UPDATED_GENERIC_MOLECULE)
            .isPrescriptionNeeded(UPDATED_IS_PRESCRIPTION_NEEDED)
            .description(UPDATED_DESCRIPTION)
            .mrp(UPDATED_MRP)
            .isInsuranceCovered(UPDATED_IS_INSURANCE_COVERED)
            .type(UPDATED_TYPE)
            .isConsumable(UPDATED_IS_CONSUMABLE)
            .isReturnable(UPDATED_IS_RETURNABLE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMedicine))
            )
            .andExpect(status().isOk());

        // Validate the Medicine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMedicineUpdatableFieldsEquals(partialUpdatedMedicine, getPersistedMedicine(partialUpdatedMedicine));
    }

    @Test
    @Transactional
    void patchNonExistingMedicine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicine.setId(longCount.incrementAndGet());

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicineDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medicineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicine.setId(longCount.incrementAndGet());

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(medicineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        medicine.setId(longCount.incrementAndGet());

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(medicineDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicine() throws Exception {
        // Initialize the database
        insertedMedicine = medicineRepository.saveAndFlush(medicine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the medicine
        restMedicineMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return medicineRepository.count();
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

    protected Medicine getPersistedMedicine(Medicine medicine) {
        return medicineRepository.findById(medicine.getId()).orElseThrow();
    }

    protected void assertPersistedMedicineToMatchAllProperties(Medicine expectedMedicine) {
        assertMedicineAllPropertiesEquals(expectedMedicine, getPersistedMedicine(expectedMedicine));
    }

    protected void assertPersistedMedicineToMatchUpdatableProperties(Medicine expectedMedicine) {
        assertMedicineAllUpdatablePropertiesEquals(expectedMedicine, getPersistedMedicine(expectedMedicine));
    }
}
