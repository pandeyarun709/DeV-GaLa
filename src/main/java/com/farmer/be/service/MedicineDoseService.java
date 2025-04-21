package com.farmer.be.service;

import com.farmer.be.service.dto.MedicineDoseDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.MedicineDose}.
 */
public interface MedicineDoseService {
    /**
     * Save a medicineDose.
     *
     * @param medicineDoseDTO the entity to save.
     * @return the persisted entity.
     */
    MedicineDoseDTO save(MedicineDoseDTO medicineDoseDTO);

    /**
     * Updates a medicineDose.
     *
     * @param medicineDoseDTO the entity to update.
     * @return the persisted entity.
     */
    MedicineDoseDTO update(MedicineDoseDTO medicineDoseDTO);

    /**
     * Partially updates a medicineDose.
     *
     * @param medicineDoseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedicineDoseDTO> partialUpdate(MedicineDoseDTO medicineDoseDTO);

    /**
     * Get all the medicineDoses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicineDoseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" medicineDose.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicineDoseDTO> findOne(Long id);

    /**
     * Delete the "id" medicineDose.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
