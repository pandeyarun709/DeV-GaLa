package com.farmer.be.service;

import com.farmer.be.service.dto.MedicineBatchDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.MedicineBatch}.
 */
public interface MedicineBatchService {
    /**
     * Save a medicineBatch.
     *
     * @param medicineBatchDTO the entity to save.
     * @return the persisted entity.
     */
    MedicineBatchDTO save(MedicineBatchDTO medicineBatchDTO);

    /**
     * Updates a medicineBatch.
     *
     * @param medicineBatchDTO the entity to update.
     * @return the persisted entity.
     */
    MedicineBatchDTO update(MedicineBatchDTO medicineBatchDTO);

    /**
     * Partially updates a medicineBatch.
     *
     * @param medicineBatchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedicineBatchDTO> partialUpdate(MedicineBatchDTO medicineBatchDTO);

    /**
     * Get all the medicineBatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicineBatchDTO> findAll(Pageable pageable);

    /**
     * Get the "id" medicineBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicineBatchDTO> findOne(Long id);

    /**
     * Delete the "id" medicineBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
