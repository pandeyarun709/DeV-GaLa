package com.farmer.be.service;

import com.farmer.be.service.dto.BedDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Bed}.
 */
public interface BedService {
    /**
     * Save a bed.
     *
     * @param bedDTO the entity to save.
     * @return the persisted entity.
     */
    BedDTO save(BedDTO bedDTO);

    /**
     * Updates a bed.
     *
     * @param bedDTO the entity to update.
     * @return the persisted entity.
     */
    BedDTO update(BedDTO bedDTO);

    /**
     * Partially updates a bed.
     *
     * @param bedDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BedDTO> partialUpdate(BedDTO bedDTO);

    /**
     * Get all the beds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bed.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BedDTO> findOne(Long id);

    /**
     * Delete the "id" bed.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
