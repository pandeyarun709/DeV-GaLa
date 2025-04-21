package com.farmer.be.web.rest;

import com.farmer.be.repository.BedRepository;
import com.farmer.be.service.BedService;
import com.farmer.be.service.dto.BedDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.Bed}.
 */
@RestController
@RequestMapping("/api/beds")
public class BedResource {

    private static final Logger LOG = LoggerFactory.getLogger(BedResource.class);

    private static final String ENTITY_NAME = "bed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedService bedService;

    private final BedRepository bedRepository;

    public BedResource(BedService bedService, BedRepository bedRepository) {
        this.bedService = bedService;
        this.bedRepository = bedRepository;
    }

    /**
     * {@code POST  /beds} : Create a new bed.
     *
     * @param bedDTO the bedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedDTO, or with status {@code 400 (Bad Request)} if the bed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BedDTO> createBed(@RequestBody BedDTO bedDTO) throws URISyntaxException {
        LOG.debug("REST request to save Bed : {}", bedDTO);
        if (bedDTO.getId() != null) {
            throw new BadRequestAlertException("A new bed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bedDTO = bedService.save(bedDTO);
        return ResponseEntity.created(new URI("/api/beds/" + bedDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bedDTO.getId().toString()))
            .body(bedDTO);
    }

    /**
     * {@code PUT  /beds/:id} : Updates an existing bed.
     *
     * @param id the id of the bedDTO to save.
     * @param bedDTO the bedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedDTO,
     * or with status {@code 400 (Bad Request)} if the bedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BedDTO> updateBed(@PathVariable(value = "id", required = false) final Long id, @RequestBody BedDTO bedDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update Bed : {}, {}", id, bedDTO);
        if (bedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bedDTO = bedService.update(bedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedDTO.getId().toString()))
            .body(bedDTO);
    }

    /**
     * {@code PATCH  /beds/:id} : Partial updates given fields of an existing bed, field will ignore if it is null
     *
     * @param id the id of the bedDTO to save.
     * @param bedDTO the bedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedDTO,
     * or with status {@code 400 (Bad Request)} if the bedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BedDTO> partialUpdateBed(@PathVariable(value = "id", required = false) final Long id, @RequestBody BedDTO bedDTO)
        throws URISyntaxException {
        LOG.debug("REST request to partial update Bed partially : {}, {}", id, bedDTO);
        if (bedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BedDTO> result = bedService.partialUpdate(bedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /beds} : get all the beds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BedDTO>> getAllBeds(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Beds");
        Page<BedDTO> page = bedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beds/:id} : get the "id" bed.
     *
     * @param id the id of the bedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BedDTO> getBed(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Bed : {}", id);
        Optional<BedDTO> bedDTO = bedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bedDTO);
    }

    /**
     * {@code DELETE  /beds/:id} : delete the "id" bed.
     *
     * @param id the id of the bedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBed(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Bed : {}", id);
        bedService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
