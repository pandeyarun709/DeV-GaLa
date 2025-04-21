package com.farmer.be.repository;

import com.farmer.be.domain.MedicineBatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MedicineBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicineBatchRepository extends JpaRepository<MedicineBatch, Long> {}
