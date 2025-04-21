package com.farmer.be.web.rest;

import com.farmer.be.repository.ReferralDoctorRepository;
import com.farmer.be.service.ReferralDoctorService;
import com.farmer.be.service.dto.ReferralDoctorDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.ReferralDoctor}.
 */
@RestController
@RequestMapping("/api/referral-doctors")
public class ReferralDoctorResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReferralDoctorResource.class);

    private static final String ENTITY_NAME = "referralDoctor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferralDoctorService referralDoctorService;

    private final ReferralDoctorRepository referralDoctorRepository;

    public ReferralDoctorResource(ReferralDoctorService referralDoctorService, ReferralDoctorRepository referralDoctorRepository) {
        this.referralDoctorService = referralDoctorService;
        this.referralDoctorRepository = referralDoctorRepository;
    }

    /**
     * {@code POST  /referral-doctors} : Create a new referralDoctor.
     *
     * @param referralDoctorDTO the referralDoctorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referralDoctorDTO, or with status {@code 400 (Bad Request)} if the referralDoctor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ReferralDoctorDTO> createReferralDoctor(@RequestBody ReferralDoctorDTO referralDoctorDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ReferralDoctor : {}", referralDoctorDTO);
        if (referralDoctorDTO.getId() != null) {
            throw new BadRequestAlertException("A new referralDoctor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        referralDoctorDTO = referralDoctorService.save(referralDoctorDTO);
        return ResponseEntity.created(new URI("/api/referral-doctors/" + referralDoctorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, referralDoctorDTO.getId().toString()))
            .body(referralDoctorDTO);
    }

    /**
     * {@code PUT  /referral-doctors/:id} : Updates an existing referralDoctor.
     *
     * @param id the id of the referralDoctorDTO to save.
     * @param referralDoctorDTO the referralDoctorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referralDoctorDTO,
     * or with status {@code 400 (Bad Request)} if the referralDoctorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referralDoctorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReferralDoctorDTO> updateReferralDoctor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferralDoctorDTO referralDoctorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ReferralDoctor : {}, {}", id, referralDoctorDTO);
        if (referralDoctorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referralDoctorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referralDoctorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        referralDoctorDTO = referralDoctorService.update(referralDoctorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referralDoctorDTO.getId().toString()))
            .body(referralDoctorDTO);
    }

    /**
     * {@code PATCH  /referral-doctors/:id} : Partial updates given fields of an existing referralDoctor, field will ignore if it is null
     *
     * @param id the id of the referralDoctorDTO to save.
     * @param referralDoctorDTO the referralDoctorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referralDoctorDTO,
     * or with status {@code 400 (Bad Request)} if the referralDoctorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the referralDoctorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the referralDoctorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReferralDoctorDTO> partialUpdateReferralDoctor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferralDoctorDTO referralDoctorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ReferralDoctor partially : {}, {}", id, referralDoctorDTO);
        if (referralDoctorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referralDoctorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referralDoctorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReferralDoctorDTO> result = referralDoctorService.partialUpdate(referralDoctorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referralDoctorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /referral-doctors} : get all the referralDoctors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referralDoctors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ReferralDoctorDTO>> getAllReferralDoctors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ReferralDoctors");
        Page<ReferralDoctorDTO> page = referralDoctorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /referral-doctors/:id} : get the "id" referralDoctor.
     *
     * @param id the id of the referralDoctorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referralDoctorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReferralDoctorDTO> getReferralDoctor(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ReferralDoctor : {}", id);
        Optional<ReferralDoctorDTO> referralDoctorDTO = referralDoctorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referralDoctorDTO);
    }

    /**
     * {@code DELETE  /referral-doctors/:id} : delete the "id" referralDoctor.
     *
     * @param id the id of the referralDoctorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReferralDoctor(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ReferralDoctor : {}", id);
        referralDoctorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
