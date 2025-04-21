package com.farmer.be.service;

import com.farmer.be.service.dto.MedicineDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Medicine}.
 */
public interface MedicineService {
    /**
     * Save a medicine.
     *
     * @param medicineDTO the entity to save.
     * @return the persisted entity.
     */
    MedicineDTO save(MedicineDTO medicineDTO);

    /**
     * Updates a medicine.
     *
     * @param medicineDTO the entity to update.
     * @return the persisted entity.
     */
    MedicineDTO update(MedicineDTO medicineDTO);

    /**
     * Partially updates a medicine.
     *
     * @param medicineDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedicineDTO> partialUpdate(MedicineDTO medicineDTO);

    /**
     * Get all the medicines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicineDTO> findAll(Pageable pageable);

    /**
     * Get the "id" medicine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicineDTO> findOne(Long id);

    /**
     * Delete the "id" medicine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
