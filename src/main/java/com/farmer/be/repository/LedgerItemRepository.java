package com.farmer.be.repository;

import com.farmer.be.domain.LedgerItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LedgerItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LedgerItemRepository extends JpaRepository<LedgerItem, Long> {}
