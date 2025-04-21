package com.farmer.be.repository;

import com.farmer.be.domain.MedicineDose;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MedicineDose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicineDoseRepository extends JpaRepository<MedicineDose, Long> {}
