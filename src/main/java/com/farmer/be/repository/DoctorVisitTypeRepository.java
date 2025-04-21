package com.farmer.be.repository;

import com.farmer.be.domain.DoctorVisitType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DoctorVisitType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorVisitTypeRepository extends JpaRepository<DoctorVisitType, Long> {}
