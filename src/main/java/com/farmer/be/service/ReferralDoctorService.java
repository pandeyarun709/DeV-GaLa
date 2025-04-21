package com.farmer.be.service;

import com.farmer.be.service.dto.ReferralDoctorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.ReferralDoctor}.
 */
public interface ReferralDoctorService {
    /**
     * Save a referralDoctor.
     *
     * @param referralDoctorDTO the entity to save.
     * @return the persisted entity.
     */
    ReferralDoctorDTO save(ReferralDoctorDTO referralDoctorDTO);

    /**
     * Updates a referralDoctor.
     *
     * @param referralDoctorDTO the entity to update.
     * @return the persisted entity.
     */
    ReferralDoctorDTO update(ReferralDoctorDTO referralDoctorDTO);

    /**
     * Partially updates a referralDoctor.
     *
     * @param referralDoctorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReferralDoctorDTO> partialUpdate(ReferralDoctorDTO referralDoctorDTO);

    /**
     * Get all the referralDoctors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReferralDoctorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" referralDoctor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReferralDoctorDTO> findOne(Long id);

    /**
     * Delete the "id" referralDoctor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
