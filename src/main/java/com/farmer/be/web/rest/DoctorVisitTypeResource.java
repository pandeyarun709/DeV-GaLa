package com.farmer.be.web.rest;

import com.farmer.be.repository.DoctorVisitTypeRepository;
import com.farmer.be.service.DoctorVisitTypeService;
import com.farmer.be.service.dto.DoctorVisitTypeDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.DoctorVisitType}.
 */
@RestController
@RequestMapping("/api/doctor-visit-types")
public class DoctorVisitTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(DoctorVisitTypeResource.class);

    private static final String ENTITY_NAME = "doctorVisitType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorVisitTypeService doctorVisitTypeService;

    private final DoctorVisitTypeRepository doctorVisitTypeRepository;

    public DoctorVisitTypeResource(DoctorVisitTypeService doctorVisitTypeService, DoctorVisitTypeRepository doctorVisitTypeRepository) {
        this.doctorVisitTypeService = doctorVisitTypeService;
        this.doctorVisitTypeRepository = doctorVisitTypeRepository;
    }

    /**
     * {@code POST  /doctor-visit-types} : Create a new doctorVisitType.
     *
     * @param doctorVisitTypeDTO the doctorVisitTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorVisitTypeDTO, or with status {@code 400 (Bad Request)} if the doctorVisitType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DoctorVisitTypeDTO> createDoctorVisitType(@RequestBody DoctorVisitTypeDTO doctorVisitTypeDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DoctorVisitType : {}", doctorVisitTypeDTO);
        if (doctorVisitTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new doctorVisitType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        doctorVisitTypeDTO = doctorVisitTypeService.save(doctorVisitTypeDTO);
        return ResponseEntity.created(new URI("/api/doctor-visit-types/" + doctorVisitTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, doctorVisitTypeDTO.getId().toString()))
            .body(doctorVisitTypeDTO);
    }

    /**
     * {@code PUT  /doctor-visit-types/:id} : Updates an existing doctorVisitType.
     *
     * @param id the id of the doctorVisitTypeDTO to save.
     * @param doctorVisitTypeDTO the doctorVisitTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorVisitTypeDTO,
     * or with status {@code 400 (Bad Request)} if the doctorVisitTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorVisitTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DoctorVisitTypeDTO> updateDoctorVisitType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorVisitTypeDTO doctorVisitTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DoctorVisitType : {}, {}", id, doctorVisitTypeDTO);
        if (doctorVisitTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorVisitTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorVisitTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        doctorVisitTypeDTO = doctorVisitTypeService.update(doctorVisitTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorVisitTypeDTO.getId().toString()))
            .body(doctorVisitTypeDTO);
    }

    /**
     * {@code PATCH  /doctor-visit-types/:id} : Partial updates given fields of an existing doctorVisitType, field will ignore if it is null
     *
     * @param id the id of the doctorVisitTypeDTO to save.
     * @param doctorVisitTypeDTO the doctorVisitTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorVisitTypeDTO,
     * or with status {@code 400 (Bad Request)} if the doctorVisitTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the doctorVisitTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the doctorVisitTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DoctorVisitTypeDTO> partialUpdateDoctorVisitType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DoctorVisitTypeDTO doctorVisitTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DoctorVisitType partially : {}, {}", id, doctorVisitTypeDTO);
        if (doctorVisitTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctorVisitTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorVisitTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DoctorVisitTypeDTO> result = doctorVisitTypeService.partialUpdate(doctorVisitTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctorVisitTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /doctor-visit-types} : get all the doctorVisitTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorVisitTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DoctorVisitTypeDTO>> getAllDoctorVisitTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DoctorVisitTypes");
        Page<DoctorVisitTypeDTO> page = doctorVisitTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doctor-visit-types/:id} : get the "id" doctorVisitType.
     *
     * @param id the id of the doctorVisitTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorVisitTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoctorVisitTypeDTO> getDoctorVisitType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DoctorVisitType : {}", id);
        Optional<DoctorVisitTypeDTO> doctorVisitTypeDTO = doctorVisitTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorVisitTypeDTO);
    }

    /**
     * {@code DELETE  /doctor-visit-types/:id} : delete the "id" doctorVisitType.
     *
     * @param id the id of the doctorVisitTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorVisitType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DoctorVisitType : {}", id);
        doctorVisitTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
