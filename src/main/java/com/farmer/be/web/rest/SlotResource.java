package com.farmer.be.web.rest;

import com.farmer.be.repository.SlotRepository;
import com.farmer.be.service.SlotService;
import com.farmer.be.service.dto.SlotDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.Slot}.
 */
@RestController
@RequestMapping("/api/slots")
public class SlotResource {

    private static final Logger LOG = LoggerFactory.getLogger(SlotResource.class);

    private static final String ENTITY_NAME = "slot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SlotService slotService;

    private final SlotRepository slotRepository;

    public SlotResource(SlotService slotService, SlotRepository slotRepository) {
        this.slotService = slotService;
        this.slotRepository = slotRepository;
    }

    /**
     * {@code POST  /slots} : Create a new slot.
     *
     * @param slotDTO the slotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new slotDTO, or with status {@code 400 (Bad Request)} if the slot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SlotDTO> createSlot(@RequestBody SlotDTO slotDTO) throws URISyntaxException {
        LOG.debug("REST request to save Slot : {}", slotDTO);
        if (slotDTO.getId() != null) {
            throw new BadRequestAlertException("A new slot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        slotDTO = slotService.save(slotDTO);
        return ResponseEntity.created(new URI("/api/slots/" + slotDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, slotDTO.getId().toString()))
            .body(slotDTO);
    }

    /**
     * {@code PUT  /slots/:id} : Updates an existing slot.
     *
     * @param id the id of the slotDTO to save.
     * @param slotDTO the slotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slotDTO,
     * or with status {@code 400 (Bad Request)} if the slotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the slotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SlotDTO> updateSlot(@PathVariable(value = "id", required = false) final Long id, @RequestBody SlotDTO slotDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update Slot : {}, {}", id, slotDTO);
        if (slotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, slotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!slotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        slotDTO = slotService.update(slotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, slotDTO.getId().toString()))
            .body(slotDTO);
    }

    /**
     * {@code PATCH  /slots/:id} : Partial updates given fields of an existing slot, field will ignore if it is null
     *
     * @param id the id of the slotDTO to save.
     * @param slotDTO the slotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated slotDTO,
     * or with status {@code 400 (Bad Request)} if the slotDTO is not valid,
     * or with status {@code 404 (Not Found)} if the slotDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the slotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SlotDTO> partialUpdateSlot(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SlotDTO slotDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Slot partially : {}, {}", id, slotDTO);
        if (slotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, slotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!slotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SlotDTO> result = slotService.partialUpdate(slotDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, slotDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /slots} : get all the slots.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of slots in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SlotDTO>> getAllSlots(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Slots");
        Page<SlotDTO> page = slotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /slots/:id} : get the "id" slot.
     *
     * @param id the id of the slotDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the slotDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SlotDTO> getSlot(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Slot : {}", id);
        Optional<SlotDTO> slotDTO = slotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(slotDTO);
    }

    /**
     * {@code DELETE  /slots/:id} : delete the "id" slot.
     *
     * @param id the id of the slotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Slot : {}", id);
        slotService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
