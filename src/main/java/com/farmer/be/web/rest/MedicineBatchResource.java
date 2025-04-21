package com.farmer.be.web.rest;

import com.farmer.be.repository.MedicineBatchRepository;
import com.farmer.be.service.MedicineBatchService;
import com.farmer.be.service.dto.MedicineBatchDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.MedicineBatch}.
 */
@RestController
@RequestMapping("/api/medicine-batches")
public class MedicineBatchResource {

    private static final Logger LOG = LoggerFactory.getLogger(MedicineBatchResource.class);

    private static final String ENTITY_NAME = "medicineBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineBatchService medicineBatchService;

    private final MedicineBatchRepository medicineBatchRepository;

    public MedicineBatchResource(MedicineBatchService medicineBatchService, MedicineBatchRepository medicineBatchRepository) {
        this.medicineBatchService = medicineBatchService;
        this.medicineBatchRepository = medicineBatchRepository;
    }

    /**
     * {@code POST  /medicine-batches} : Create a new medicineBatch.
     *
     * @param medicineBatchDTO the medicineBatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicineBatchDTO, or with status {@code 400 (Bad Request)} if the medicineBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MedicineBatchDTO> createMedicineBatch(@RequestBody MedicineBatchDTO medicineBatchDTO) throws URISyntaxException {
        LOG.debug("REST request to save MedicineBatch : {}", medicineBatchDTO);
        if (medicineBatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicineBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        medicineBatchDTO = medicineBatchService.save(medicineBatchDTO);
        return ResponseEntity.created(new URI("/api/medicine-batches/" + medicineBatchDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, medicineBatchDTO.getId().toString()))
            .body(medicineBatchDTO);
    }

    /**
     * {@code PUT  /medicine-batches/:id} : Updates an existing medicineBatch.
     *
     * @param id the id of the medicineBatchDTO to save.
     * @param medicineBatchDTO the medicineBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineBatchDTO,
     * or with status {@code 400 (Bad Request)} if the medicineBatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicineBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicineBatchDTO> updateMedicineBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicineBatchDTO medicineBatchDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MedicineBatch : {}, {}", id, medicineBatchDTO);
        if (medicineBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicineBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        medicineBatchDTO = medicineBatchService.update(medicineBatchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicineBatchDTO.getId().toString()))
            .body(medicineBatchDTO);
    }

    /**
     * {@code PATCH  /medicine-batches/:id} : Partial updates given fields of an existing medicineBatch, field will ignore if it is null
     *
     * @param id the id of the medicineBatchDTO to save.
     * @param medicineBatchDTO the medicineBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineBatchDTO,
     * or with status {@code 400 (Bad Request)} if the medicineBatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medicineBatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicineBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedicineBatchDTO> partialUpdateMedicineBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicineBatchDTO medicineBatchDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MedicineBatch partially : {}, {}", id, medicineBatchDTO);
        if (medicineBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicineBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicineBatchDTO> result = medicineBatchService.partialUpdate(medicineBatchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicineBatchDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /medicine-batches} : get all the medicineBatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicineBatches in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MedicineBatchDTO>> getAllMedicineBatches(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MedicineBatches");
        Page<MedicineBatchDTO> page = medicineBatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicine-batches/:id} : get the "id" medicineBatch.
     *
     * @param id the id of the medicineBatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicineBatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicineBatchDTO> getMedicineBatch(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MedicineBatch : {}", id);
        Optional<MedicineBatchDTO> medicineBatchDTO = medicineBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicineBatchDTO);
    }

    /**
     * {@code DELETE  /medicine-batches/:id} : delete the "id" medicineBatch.
     *
     * @param id the id of the medicineBatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineBatch(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MedicineBatch : {}", id);
        medicineBatchService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
