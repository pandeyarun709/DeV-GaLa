package com.farmer.be.repository;

import com.farmer.be.domain.DiagnosticTest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DiagnosticTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosticTestRepository extends JpaRepository<DiagnosticTest, Long> {}
