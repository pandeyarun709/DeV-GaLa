package com.farmer.be.service;

import com.farmer.be.service.dto.PatientRegistrationDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.PatientRegistrationDetails}.
 */
public interface PatientRegistrationDetailsService {
    /**
     * Save a patientRegistrationDetails.
     *
     * @param patientRegistrationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    PatientRegistrationDetailsDTO save(PatientRegistrationDetailsDTO patientRegistrationDetailsDTO);

    /**
     * Updates a patientRegistrationDetails.
     *
     * @param patientRegistrationDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    PatientRegistrationDetailsDTO update(PatientRegistrationDetailsDTO patientRegistrationDetailsDTO);

    /**
     * Partially updates a patientRegistrationDetails.
     *
     * @param patientRegistrationDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatientRegistrationDetailsDTO> partialUpdate(PatientRegistrationDetailsDTO patientRegistrationDetailsDTO);

    /**
     * Get all the patientRegistrationDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientRegistrationDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" patientRegistrationDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientRegistrationDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" patientRegistrationDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
