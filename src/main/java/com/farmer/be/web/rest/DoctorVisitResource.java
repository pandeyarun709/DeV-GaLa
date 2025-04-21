package com.farmer.be.web.rest;

import com.farmer.be.repository.DoctorVisitRepository;
import com.farmer.be.service.DoctorVisitService;
import com.farmer.be.service.dto.DoctorVisitDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.DoctorVisit}.
 */
@RestController
@RequestMapping("/api/doctor-visits")
public class DoctorVisitResource {

    private static final Logger LOG = LoggerFactory.getLogger(DoctorVisitResource.class);

    private static final String ENTITY_NAME = "doctorVisit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorVisitService doctorVisitService;

    private final DoctorVisitRepository doctorVisitRepository;

    public DoctorVisitResource(DoctorVisitService doctorVisitService, DoctorVisitRepository doctorVisitRepository) {
        this.doctorVisitService = doctorVisitService;
        this.doctorVisitRepository = doctorVisitRepository;
    }

    /**
     * {@code POST  /doctor-visits} : Create a new doctorVisit.
     *
     * @param doctorVisitDTO the doctorVisitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorVisitDTO, or with status {@code 400 (Bad Request)} if the doctorVisit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DoctorVisitDTO> createDoctorVisit(@RequestBody DoctorVisitDTO doctorVisitDTO) throws URISyntaxException {
        LOG.debug("REST request to save DoctorVisit : {}", doctorVisitDTO);
        if (doctorVisitDTO.getId() != null) {
            throw new BadRequestAlertException("A new doctorVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        doctorVisitDTO = doctorVisitService.save(doctorVisitDTO);
        return ResponseEntity.created(new URI("/api/doctor-visits/" + doctorVisitDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, doctorVisitDTO.getId().toString()))
            .body(doctorVisitDTO);
    }

    /**
     * {@code PUT  /doctor-visits/:id} : Updates an existing doctorVisit.
     *
     * @param id the id of the doctorVisitDTO to save.
     * @param doctorVisitDTO the doctorVisitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorVisitDTO,
     * or with status {@code 400 (Bad Request)} if the doctorVisitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorVisitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DoctorVisitDTO> updateDoctorVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorVisitDTO doctorVisitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DoctorVisit : {}, {}", id, doctorVisitDTO);
        if (doctorVisitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorVisitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorVisitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        doctorVisitDTO = doctorVisitService.update(doctorVisitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorVisitDTO.getId().toString()))
            .body(doctorVisitDTO);
    }

    /**
     * {@code PATCH  /doctor-visits/:id} : Partial updates given fields of an existing doctorVisit, field will ignore if it is null
     *
     * @param id the id of the doctorVisitDTO to save.
     * @param doctorVisitDTO the doctorVisitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorVisitDTO,
     * or with status {@code 400 (Bad Request)} if the doctorVisitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the doctorVisitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the doctorVisitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DoctorVisitDTO> partialUpdateDoctorVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorVisitDTO doctorVisitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DoctorVisit partially : {}, {}", id, doctorVisitDTO);
        if (doctorVisitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorVisitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorVisitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DoctorVisitDTO> result = doctorVisitService.partialUpdate(doctorVisitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorVisitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /doctor-visits} : get all the doctorVisits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorVisits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DoctorVisitDTO>> getAllDoctorVisits(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of DoctorVisits");
        Page<DoctorVisitDTO> page = doctorVisitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doctor-visits/:id} : get the "id" doctorVisit.
     *
     * @param id the id of the doctorVisitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorVisitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorVisitDTO> getDoctorVisit(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DoctorVisit : {}", id);
        Optional<DoctorVisitDTO> doctorVisitDTO = doctorVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorVisitDTO);
    }

    /**
     * {@code DELETE  /doctor-visits/:id} : delete the "id" doctorVisit.
     *
     * @param id the id of the doctorVisitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorVisit(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DoctorVisit : {}", id);
        doctorVisitService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
