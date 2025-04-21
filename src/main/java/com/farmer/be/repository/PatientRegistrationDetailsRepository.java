package com.farmer.be.repository;

import com.farmer.be.domain.PatientRegistrationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PatientRegistrationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRegistrationDetailsRepository extends JpaRepository<PatientRegistrationDetails, Long> {}
