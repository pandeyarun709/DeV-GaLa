package com.farmer.be.web.rest;

import com.farmer.be.repository.QualificationRepository;
import com.farmer.be.service.QualificationService;
import com.farmer.be.service.dto.QualificationDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.Qualification}.
 */
@RestController
@RequestMapping("/api/qualifications")
public class QualificationResource {

    private static final Logger LOG = LoggerFactory.getLogger(QualificationResource.class);

    private static final String ENTITY_NAME = "qualification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QualificationService qualificationService;

    private final QualificationRepository qualificationRepository;

    public QualificationResource(QualificationService qualificationService, QualificationRepository qualificationRepository) {
        this.qualificationService = qualificationService;
        this.qualificationRepository = qualificationRepository;
    }

    /**
     * {@code POST  /qualifications} : Create a new qualification.
     *
     * @param qualificationDTO the qualificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qualificationDTO, or with status {@code 400 (Bad Request)} if the qualification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<QualificationDTO> createQualification(@RequestBody QualificationDTO qualificationDTO) throws URISyntaxException {
        LOG.debug("REST request to save Qualification : {}", qualificationDTO);
        if (qualificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new qualification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        qualificationDTO = qualificationService.save(qualificationDTO);
        return ResponseEntity.created(new URI("/api/qualifications/" + qualificationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, qualificationDTO.getId().toString()))
            .body(qualificationDTO);
    }

    /**
     * {@code PUT  /qualifications/:id} : Updates an existing qualification.
     *
     * @param id the id of the qualificationDTO to save.
     * @param qualificationDTO the qualificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualificationDTO,
     * or with status {@code 400 (Bad Request)} if the qualificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qualificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QualificationDTO> updateQualification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QualificationDTO qualificationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Qualification : {}, {}", id, qualificationDTO);
        if (qualificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        qualificationDTO = qualificationService.update(qualificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qualificationDTO.getId().toString()))
            .body(qualificationDTO);
    }

    /**
     * {@code PATCH  /qualifications/:id} : Partial updates given fields of an existing qualification, field will ignore if it is null
     *
     * @param id the id of the qualificationDTO to save.
     * @param qualificationDTO the qualificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qualificationDTO,
     * or with status {@code 400 (Bad Request)} if the qualificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qualificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qualificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QualificationDTO> partialUpdateQualification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QualificationDTO qualificationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Qualification partially : {}, {}", id, qualificationDTO);
        if (qualificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qualificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qualificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QualificationDTO> result = qualificationService.partialUpdate(qualificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qualificationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qualifications} : get all the qualifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qualifications in body.
     */
    @GetMapping("")
    public ResponseEntity<List<QualificationDTO>> getAllQualifications(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Qualifications");
        Page<QualificationDTO> page = qualificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qualifications/:id} : get the "id" qualification.
     *
     * @param id the id of the qualificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qualificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QualificationDTO> getQualification(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Qualification : {}", id);
        Optional<QualificationDTO> qualificationDTO = qualificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qualificationDTO);
    }

    /**
     * {@code DELETE  /qualifications/:id} : delete the "id" qualification.
     *
     * @param id the id of the qualificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQualification(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Qualification : {}", id);
        qualificationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
