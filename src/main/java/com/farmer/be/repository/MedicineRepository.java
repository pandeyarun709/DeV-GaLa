package com.farmer.be.repository;

import com.farmer.be.domain.Medicine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Medicine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {}
