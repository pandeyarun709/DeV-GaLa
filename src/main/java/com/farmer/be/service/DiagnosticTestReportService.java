package com.farmer.be.service;

import com.farmer.be.service.dto.DiagnosticTestReportDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.DiagnosticTestReport}.
 */
public interface DiagnosticTestReportService {
    /**
     * Save a diagnosticTestReport.
     *
     * @param diagnosticTestReportDTO the entity to save.
     * @return the persisted entity.
     */
    DiagnosticTestReportDTO save(DiagnosticTestReportDTO diagnosticTestReportDTO);

    /**
     * Updates a diagnosticTestReport.
     *
     * @param diagnosticTestReportDTO the entity to update.
     * @return the persisted entity.
     */
    DiagnosticTestReportDTO update(DiagnosticTestReportDTO diagnosticTestReportDTO);

    /**
     * Partially updates a diagnosticTestReport.
     *
     * @param diagnosticTestReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiagnosticTestReportDTO> partialUpdate(DiagnosticTestReportDTO diagnosticTestReportDTO);

    /**
     * Get all the diagnosticTestReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiagnosticTestReportDTO> findAll(Pageable pageable);

    /**
     * Get the "id" diagnosticTestReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiagnosticTestReportDTO> findOne(Long id);

    /**
     * Delete the "id" diagnosticTestReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
