package com.farmer.be.repository;

import com.farmer.be.domain.DoctorVisit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DoctorVisit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorVisitRepository extends JpaRepository<DoctorVisit, Long> {}
