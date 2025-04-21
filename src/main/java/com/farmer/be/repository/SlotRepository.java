package com.farmer.be.repository;

import com.farmer.be.domain.Slot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Slot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {}
