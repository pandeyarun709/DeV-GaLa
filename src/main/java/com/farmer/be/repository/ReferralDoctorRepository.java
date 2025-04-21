package com.farmer.be.repository;

import com.farmer.be.domain.ReferralDoctor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReferralDoctor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferralDoctorRepository extends JpaRepository<ReferralDoctor, Long> {}
