package com.farmer.be.service;

import com.farmer.be.service.dto.DoctorVisitTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.DoctorVisitType}.
 */
public interface DoctorVisitTypeService {
    /**
     * Save a doctorVisitType.
     *
     * @param doctorVisitTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DoctorVisitTypeDTO save(DoctorVisitTypeDTO doctorVisitTypeDTO);

    /**
     * Updates a doctorVisitType.
     *
     * @param doctorVisitTypeDTO the entity to update.
     * @return the persisted entity.
     */
    DoctorVisitTypeDTO update(DoctorVisitTypeDTO doctorVisitTypeDTO);

    /**
     * Partially updates a doctorVisitType.
     *
     * @param doctorVisitTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DoctorVisitTypeDTO> partialUpdate(DoctorVisitTypeDTO doctorVisitTypeDTO);

    /**
     * Get all the doctorVisitTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DoctorVisitTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" doctorVisitType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoctorVisitTypeDTO> findOne(Long id);

    /**
     * Delete the "id" doctorVisitType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
