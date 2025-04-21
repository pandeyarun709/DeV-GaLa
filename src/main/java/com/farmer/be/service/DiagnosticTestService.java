package com.farmer.be.service;

import com.farmer.be.service.dto.DiagnosticTestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.DiagnosticTest}.
 */
public interface DiagnosticTestService {
    /**
     * Save a diagnosticTest.
     *
     * @param diagnosticTestDTO the entity to save.
     * @return the persisted entity.
     */
    DiagnosticTestDTO save(DiagnosticTestDTO diagnosticTestDTO);

    /**
     * Updates a diagnosticTest.
     *
     * @param diagnosticTestDTO the entity to update.
     * @return the persisted entity.
     */
    DiagnosticTestDTO update(DiagnosticTestDTO diagnosticTestDTO);

    /**
     * Partially updates a diagnosticTest.
     *
     * @param diagnosticTestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiagnosticTestDTO> partialUpdate(DiagnosticTestDTO diagnosticTestDTO);

    /**
     * Get all the diagnosticTests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiagnosticTestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" diagnosticTest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiagnosticTestDTO> findOne(Long id);

    /**
     * Delete the "id" diagnosticTest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
