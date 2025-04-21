package com.farmer.be.service;

import com.farmer.be.service.dto.LedgerItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.LedgerItem}.
 */
public interface LedgerItemService {
    /**
     * Save a ledgerItem.
     *
     * @param ledgerItemDTO the entity to save.
     * @return the persisted entity.
     */
    LedgerItemDTO save(LedgerItemDTO ledgerItemDTO);

    /**
     * Updates a ledgerItem.
     *
     * @param ledgerItemDTO the entity to update.
     * @return the persisted entity.
     */
    LedgerItemDTO update(LedgerItemDTO ledgerItemDTO);

    /**
     * Partially updates a ledgerItem.
     *
     * @param ledgerItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LedgerItemDTO> partialUpdate(LedgerItemDTO ledgerItemDTO);

    /**
     * Get all the ledgerItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LedgerItemDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ledgerItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LedgerItemDTO> findOne(Long id);

    /**
     * Delete the "id" ledgerItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
