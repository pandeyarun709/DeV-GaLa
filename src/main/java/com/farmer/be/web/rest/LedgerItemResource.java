package com.farmer.be.web.rest;

import com.farmer.be.repository.LedgerItemRepository;
import com.farmer.be.service.LedgerItemService;
import com.farmer.be.service.dto.LedgerItemDTO;
import com.farmer.be.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.farmer.be.domain.LedgerItem}.
 */
@RestController
@RequestMapping("/api/ledger-items")
public class LedgerItemResource {

    private static final Logger LOG = LoggerFactory.getLogger(LedgerItemResource.class);

    private static final String ENTITY_NAME = "ledgerItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LedgerItemService ledgerItemService;

    private final LedgerItemRepository ledgerItemRepository;

    public LedgerItemResource(LedgerItemService ledgerItemService, LedgerItemRepository ledgerItemRepository) {
        this.ledgerItemService = ledgerItemService;
        this.ledgerItemRepository = ledgerItemRepository;
    }

    /**
     * {@code POST  /ledger-items} : Create a new ledgerItem.
     *
     * @param ledgerItemDTO the ledgerItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ledgerItemDTO, or with status {@code 400 (Bad Request)} if the ledgerItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LedgerItemDTO> createLedgerItem(@RequestBody LedgerItemDTO ledgerItemDTO) throws URISyntaxException {
        LOG.debug("REST request to save LedgerItem : {}", ledgerItemDTO);
        if (ledgerItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new ledgerItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ledgerItemDTO = ledgerItemService.save(ledgerItemDTO);
        return ResponseEntity.created(new URI("/api/ledger-items/" + ledgerItemDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ledgerItemDTO.getId().toString()))
            .body(ledgerItemDTO);
    }

    /**
     * {@code PUT  /ledger-items/:id} : Updates an existing ledgerItem.
     *
     * @param id the id of the ledgerItemDTO to save.
     * @param ledgerItemDTO the ledgerItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ledgerItemDTO,
     * or with status {@code 400 (Bad Request)} if the ledgerItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ledgerItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LedgerItemDTO> updateLedgerItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LedgerItemDTO ledgerItemDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update LedgerItem : {}, {}", id, ledgerItemDTO);
        if (ledgerItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ledgerItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ledgerItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ledgerItemDTO = ledgerItemService.update(ledgerItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ledgerItemDTO.getId().toString()))
            .body(ledgerItemDTO);
    }

    /**
     * {@code PATCH  /ledger-items/:id} : Partial updates given fields of an existing ledgerItem, field will ignore if it is null
     *
     * @param id the id of the ledgerItemDTO to save.
     * @param ledgerItemDTO the ledgerItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ledgerItemDTO,
     * or with status {@code 400 (Bad Request)} if the ledgerItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ledgerItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ledgerItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LedgerItemDTO> partialUpdateLedgerItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LedgerItemDTO ledgerItemDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LedgerItem partially : {}, {}", id, ledgerItemDTO);
        if (ledgerItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ledgerItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ledgerItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LedgerItemDTO> result = ledgerItemService.partialUpdate(ledgerItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ledgerItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ledger-items} : get all the ledgerItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ledgerItems in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LedgerItemDTO>> getAllLedgerItems(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of LedgerItems");
        Page<LedgerItemDTO> page = ledgerItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ledger-items/:id} : get the "id" ledgerItem.
     *
     * @param id the id of the ledgerItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ledgerItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LedgerItemDTO> getLedgerItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LedgerItem : {}", id);
        Optional<LedgerItemDTO> ledgerItemDTO = ledgerItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ledgerItemDTO);
    }

    /**
     * {@code DELETE  /ledger-items/:id} : delete the "id" ledgerItem.
     *
     * @param id the id of the ledgerItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLedgerItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete LedgerItem : {}", id);
        ledgerItemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
