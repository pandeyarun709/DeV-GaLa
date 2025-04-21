package com.farmer.be.web.rest;

import com.farmer.be.repository.DiagnosticTestReportRepository;
import com.farmer.be.service.DiagnosticTestReportService;
import com.farmer.be.service.dto.DiagnosticTestReportDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.DiagnosticTestReport}.
 */
@RestController
@RequestMapping("/api/diagnostic-test-reports")
public class DiagnosticTestReportResource {

    private static final Logger LOG = LoggerFactory.getLogger(DiagnosticTestReportResource.class);

    private static final String ENTITY_NAME = "diagnosticTestReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiagnosticTestReportService diagnosticTestReportService;

    private final DiagnosticTestReportRepository diagnosticTestReportRepository;

    public DiagnosticTestReportResource(
        DiagnosticTestReportService diagnosticTestReportService,
        DiagnosticTestReportRepository diagnosticTestReportRepository
    ) {
        this.diagnosticTestReportService = diagnosticTestReportService;
        this.diagnosticTestReportRepository = diagnosticTestReportRepository;
    }

    /**
     * {@code POST  /diagnostic-test-reports} : Create a new diagnosticTestReport.
     *
     * @param diagnosticTestReportDTO the diagnosticTestReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diagnosticTestReportDTO, or with status {@code 400 (Bad Request)} if the diagnosticTestReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DiagnosticTestReportDTO> createDiagnosticTestReport(@RequestBody DiagnosticTestReportDTO diagnosticTestReportDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DiagnosticTestReport : {}", diagnosticTestReportDTO);
        if (diagnosticTestReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new diagnosticTestReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        diagnosticTestReportDTO = diagnosticTestReportService.save(diagnosticTestReportDTO);
        return ResponseEntity.created(new URI("/api/diagnostic-test-reports/" + diagnosticTestReportDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, diagnosticTestReportDTO.getId().toString()))
            .body(diagnosticTestReportDTO);
    }

    /**
     * {@code PUT  /diagnostic-test-reports/:id} : Updates an existing diagnosticTestReport.
     *
     * @param id the id of the diagnosticTestReportDTO to save.
     * @param diagnosticTestReportDTO the diagnosticTestReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosticTestReportDTO,
     * or with status {@code 400 (Bad Request)} if the diagnosticTestReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diagnosticTestReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticTestReportDTO> updateDiagnosticTestReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiagnosticTestReportDTO diagnosticTestReportDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DiagnosticTestReport : {}, {}", id, diagnosticTestReportDTO);
        if (diagnosticTestReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosticTestReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosticTestReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        diagnosticTestReportDTO = diagnosticTestReportService.update(diagnosticTestReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosticTestReportDTO.getId().toString()))
            .body(diagnosticTestReportDTO);
    }

    /**
     * {@code PATCH  /diagnostic-test-reports/:id} : Partial updates given fields of an existing diagnosticTestReport, field will ignore if it is null
     *
     * @param id the id of the diagnosticTestReportDTO to save.
     * @param diagnosticTestReportDTO the diagnosticTestReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosticTestReportDTO,
     * or with status {@code 400 (Bad Request)} if the diagnosticTestReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the diagnosticTestReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the diagnosticTestReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiagnosticTestReportDTO> partialUpdateDiagnosticTestReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiagnosticTestReportDTO diagnosticTestReportDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DiagnosticTestReport partially : {}, {}", id, diagnosticTestReportDTO);
        if (diagnosticTestReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosticTestReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosticTestReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiagnosticTestReportDTO> result = diagnosticTestReportService.partialUpdate(diagnosticTestReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosticTestReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /diagnostic-test-reports} : get all the diagnosticTestReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diagnosticTestReports in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DiagnosticTestReportDTO>> getAllDiagnosticTestReports(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DiagnosticTestReports");
        Page<DiagnosticTestReportDTO> page = diagnosticTestReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /diagnostic-test-reports/:id} : get the "id" diagnosticTestReport.
     *
     * @param id the id of the diagnosticTestReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diagnosticTestReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticTestReportDTO> getDiagnosticTestReport(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DiagnosticTestReport : {}", id);
        Optional<DiagnosticTestReportDTO> diagnosticTestReportDTO = diagnosticTestReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diagnosticTestReportDTO);
    }

    /**
     * {@code DELETE  /diagnostic-test-reports/:id} : delete the "id" diagnosticTestReport.
     *
     * @param id the id of the diagnosticTestReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosticTestReport(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DiagnosticTestReport : {}", id);
        diagnosticTestReportService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
