package com.farmer.be.service;

import com.farmer.be.service.dto.QualificationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Qualification}.
 */
public interface QualificationService {
    /**
     * Save a qualification.
     *
     * @param qualificationDTO the entity to save.
     * @return the persisted entity.
     */
    QualificationDTO save(QualificationDTO qualificationDTO);

    /**
     * Updates a qualification.
     *
     * @param qualificationDTO the entity to update.
     * @return the persisted entity.
     */
    QualificationDTO update(QualificationDTO qualificationDTO);

    /**
     * Partially updates a qualification.
     *
     * @param qualificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QualificationDTO> partialUpdate(QualificationDTO qualificationDTO);

    /**
     * Get all the qualifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QualificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" qualification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QualificationDTO> findOne(Long id);

    /**
     * Delete the "id" qualification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
