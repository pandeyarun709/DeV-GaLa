package com.farmer.be.web.rest;

import com.farmer.be.repository.MedicineDoseRepository;
import com.farmer.be.service.MedicineDoseService;
import com.farmer.be.service.dto.MedicineDoseDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.MedicineDose}.
 */
@RestController
@RequestMapping("/api/medicine-doses")
public class MedicineDoseResource {

    private static final Logger LOG = LoggerFactory.getLogger(MedicineDoseResource.class);

    private static final String ENTITY_NAME = "medicineDose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineDoseService medicineDoseService;

    private final MedicineDoseRepository medicineDoseRepository;

    public MedicineDoseResource(MedicineDoseService medicineDoseService, MedicineDoseRepository medicineDoseRepository) {
        this.medicineDoseService = medicineDoseService;
        this.medicineDoseRepository = medicineDoseRepository;
    }

    /**
     * {@code POST  /medicine-doses} : Create a new medicineDose.
     *
     * @param medicineDoseDTO the medicineDoseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicineDoseDTO, or with status {@code 400 (Bad Request)} if the medicineDose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MedicineDoseDTO> createMedicineDose(@RequestBody MedicineDoseDTO medicineDoseDTO) throws URISyntaxException {
        LOG.debug("REST request to save MedicineDose : {}", medicineDoseDTO);
        if (medicineDoseDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicineDose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        medicineDoseDTO = medicineDoseService.save(medicineDoseDTO);
        return ResponseEntity.created(new URI("/api/medicine-doses/" + medicineDoseDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, medicineDoseDTO.getId().toString()))
            .body(medicineDoseDTO);
    }

    /**
     * {@code PUT  /medicine-doses/:id} : Updates an existing medicineDose.
     *
     * @param id the id of the medicineDoseDTO to save.
     * @param medicineDoseDTO the medicineDoseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineDoseDTO,
     * or with status {@code 400 (Bad Request)} if the medicineDoseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicineDoseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MedicineDoseDTO> updateMedicineDose(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicineDoseDTO medicineDoseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MedicineDose : {}, {}", id, medicineDoseDTO);
        if (medicineDoseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicineDoseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineDoseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        medicineDoseDTO = medicineDoseService.update(medicineDoseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicineDoseDTO.getId().toString()))
            .body(medicineDoseDTO);
    }

    /**
     * {@code PATCH  /medicine-doses/:id} : Partial updates given fields of an existing medicineDose, field will ignore if it is null
     *
     * @param id the id of the medicineDoseDTO to save.
     * @param medicineDoseDTO the medicineDoseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineDoseDTO,
     * or with status {@code 400 (Bad Request)} if the medicineDoseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medicineDoseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicineDoseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedicineDoseDTO> partialUpdateMedicineDose(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MedicineDoseDTO medicineDoseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MedicineDose partially : {}, {}", id, medicineDoseDTO);
        if (medicineDoseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicineDoseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineDoseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicineDoseDTO> result = medicineDoseService.partialUpdate(medicineDoseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicineDoseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /medicine-doses} : get all the medicineDoses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicineDoses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MedicineDoseDTO>> getAllMedicineDoses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MedicineDoses");
        Page<MedicineDoseDTO> page = medicineDoseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicine-doses/:id} : get the "id" medicineDose.
     *
     * @param id the id of the medicineDoseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicineDoseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedicineDoseDTO> getMedicineDose(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MedicineDose : {}", id);
        Optional<MedicineDoseDTO> medicineDoseDTO = medicineDoseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicineDoseDTO);
    }

    /**
     * {@code DELETE  /medicine-doses/:id} : delete the "id" medicineDose.
     *
     * @param id the id of the medicineDoseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineDose(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MedicineDose : {}", id);
        medicineDoseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
