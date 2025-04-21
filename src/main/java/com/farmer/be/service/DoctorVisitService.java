package com.farmer.be.service;

import com.farmer.be.service.dto.DoctorVisitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.DoctorVisit}.
 */
public interface DoctorVisitService {
    /**
     * Save a doctorVisit.
     *
     * @param doctorVisitDTO the entity to save.
     * @return the persisted entity.
     */
    DoctorVisitDTO save(DoctorVisitDTO doctorVisitDTO);

    /**
     * Updates a doctorVisit.
     *
     * @param doctorVisitDTO the entity to update.
     * @return the persisted entity.
     */
    DoctorVisitDTO update(DoctorVisitDTO doctorVisitDTO);

    /**
     * Partially updates a doctorVisit.
     *
     * @param doctorVisitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DoctorVisitDTO> partialUpdate(DoctorVisitDTO doctorVisitDTO);

    /**
     * Get all the doctorVisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DoctorVisitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" doctorVisit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoctorVisitDTO> findOne(Long id);

    /**
     * Delete the "id" doctorVisit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
