package com.farmer.be.repository;

import com.farmer.be.domain.DiagnosticTestReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DiagnosticTestReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosticTestReportRepository extends JpaRepository<DiagnosticTestReport, Long> {}
