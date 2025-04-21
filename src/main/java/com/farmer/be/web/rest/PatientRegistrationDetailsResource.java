package com.farmer.be.web.rest;

import com.farmer.be.repository.PatientRegistrationDetailsRepository;
import com.farmer.be.service.PatientRegistrationDetailsService;
import com.farmer.be.service.dto.PatientRegistrationDetailsDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.PatientRegistrationDetails}.
 */
@RestController
@RequestMapping("/api/patient-registration-details")
public class PatientRegistrationDetailsResource {

    private static final Logger LOG = LoggerFactory.getLogger(PatientRegistrationDetailsResource.class);

    private static final String ENTITY_NAME = "patientRegistrationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientRegistrationDetailsService patientRegistrationDetailsService;

    private final PatientRegistrationDetailsRepository patientRegistrationDetailsRepository;

    public PatientRegistrationDetailsResource(
        PatientRegistrationDetailsService patientRegistrationDetailsService,
        PatientRegistrationDetailsRepository patientRegistrationDetailsRepository
    ) {
        this.patientRegistrationDetailsService = patientRegistrationDetailsService;
        this.patientRegistrationDetailsRepository = patientRegistrationDetailsRepository;
    }

    /**
     * {@code POST  /patient-registration-details} : Create a new patientRegistrationDetails.
     *
     * @param patientRegistrationDetailsDTO the patientRegistrationDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientRegistrationDetailsDTO, or with status {@code 400 (Bad Request)} if the patientRegistrationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PatientRegistrationDetailsDTO> createPatientRegistrationDetails(
        @RequestBody PatientRegistrationDetailsDTO patientRegistrationDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save PatientRegistrationDetails : {}", patientRegistrationDetailsDTO);
        if (patientRegistrationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientRegistrationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        patientRegistrationDetailsDTO = patientRegistrationDetailsService.save(patientRegistrationDetailsDTO);
        return ResponseEntity.created(new URI("/api/patient-registration-details/" + patientRegistrationDetailsDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, patientRegistrationDetailsDTO.getId().toString())
            )
            .body(patientRegistrationDetailsDTO);
    }

    /**
     * {@code PUT  /patient-registration-details/:id} : Updates an existing patientRegistrationDetails.
     *
     * @param id the id of the patientRegistrationDetailsDTO to save.
     * @param patientRegistrationDetailsDTO the patientRegistrationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientRegistrationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the patientRegistrationDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientRegistrationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientRegistrationDetailsDTO> updatePatientRegistrationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatientRegistrationDetailsDTO patientRegistrationDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PatientRegistrationDetails : {}, {}", id, patientRegistrationDetailsDTO);
        if (patientRegistrationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientRegistrationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientRegistrationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        patientRegistrationDetailsDTO = patientRegistrationDetailsService.update(patientRegistrationDetailsDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientRegistrationDetailsDTO.getId().toString())
            )
            .body(patientRegistrationDetailsDTO);
    }

    /**
     * {@code PATCH  /patient-registration-details/:id} : Partial updates given fields of an existing patientRegistrationDetails, field will ignore if it is null
     *
     * @param id the id of the patientRegistrationDetailsDTO to save.
     * @param patientRegistrationDetailsDTO the patientRegistrationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientRegistrationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the patientRegistrationDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patientRegistrationDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patientRegistrationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientRegistrationDetailsDTO> partialUpdatePatientRegistrationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PatientRegistrationDetailsDTO patientRegistrationDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PatientRegistrationDetails partially : {}, {}", id, patientRegistrationDetailsDTO);
        if (patientRegistrationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientRegistrationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientRegistrationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientRegistrationDetailsDTO> result = patientRegistrationDetailsService.partialUpdate(patientRegistrationDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientRegistrationDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patient-registration-details} : get all the patientRegistrationDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientRegistrationDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PatientRegistrationDetailsDTO>> getAllPatientRegistrationDetails(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PatientRegistrationDetails");
        Page<PatientRegistrationDetailsDTO> page = patientRegistrationDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patient-registration-details/:id} : get the "id" patientRegistrationDetails.
     *
     * @param id the id of the patientRegistrationDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientRegistrationDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientRegistrationDetailsDTO> getPatientRegistrationDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PatientRegistrationDetails : {}", id);
        Optional<PatientRegistrationDetailsDTO> patientRegistrationDetailsDTO = patientRegistrationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientRegistrationDetailsDTO);
    }

    /**
     * {@code DELETE  /patient-registration-details/:id} : delete the "id" patientRegistrationDetails.
     *
     * @param id the id of the patientRegistrationDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientRegistrationDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PatientRegistrationDetails : {}", id);
        patientRegistrationDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
