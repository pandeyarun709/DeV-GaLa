package com.farmer.be.web.rest;

import com.farmer.be.repository.DiagnosticTestRepository;
import com.farmer.be.service.DiagnosticTestService;
import com.farmer.be.service.dto.DiagnosticTestDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.DiagnosticTest}.
 */
@RestController
@RequestMapping("/api/diagnostic-tests")
public class DiagnosticTestResource {

    private static final Logger LOG = LoggerFactory.getLogger(DiagnosticTestResource.class);

    private static final String ENTITY_NAME = "diagnosticTest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiagnosticTestService diagnosticTestService;

    private final DiagnosticTestRepository diagnosticTestRepository;

    public DiagnosticTestResource(DiagnosticTestService diagnosticTestService, DiagnosticTestRepository diagnosticTestRepository) {
        this.diagnosticTestService = diagnosticTestService;
        this.diagnosticTestRepository = diagnosticTestRepository;
    }

    /**
     * {@code POST  /diagnostic-tests} : Create a new diagnosticTest.
     *
     * @param diagnosticTestDTO the diagnosticTestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diagnosticTestDTO, or with status {@code 400 (Bad Request)} if the diagnosticTest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DiagnosticTestDTO> createDiagnosticTest(@RequestBody DiagnosticTestDTO diagnosticTestDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DiagnosticTest : {}", diagnosticTestDTO);
        if (diagnosticTestDTO.getId() != null) {
            throw new BadRequestAlertException("A new diagnosticTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        diagnosticTestDTO = diagnosticTestService.save(diagnosticTestDTO);
        return ResponseEntity.created(new URI("/api/diagnostic-tests/" + diagnosticTestDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, diagnosticTestDTO.getId().toString()))
            .body(diagnosticTestDTO);
    }

    /**
     * {@code PUT  /diagnostic-tests/:id} : Updates an existing diagnosticTest.
     *
     * @param id the id of the diagnosticTestDTO to save.
     * @param diagnosticTestDTO the diagnosticTestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosticTestDTO,
     * or with status {@code 400 (Bad Request)} if the diagnosticTestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diagnosticTestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticTestDTO> updateDiagnosticTest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiagnosticTestDTO diagnosticTestDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DiagnosticTest : {}, {}", id, diagnosticTestDTO);
        if (diagnosticTestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosticTestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosticTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        diagnosticTestDTO = diagnosticTestService.update(diagnosticTestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosticTestDTO.getId().toString()))
            .body(diagnosticTestDTO);
    }

    /**
     * {@code PATCH  /diagnostic-tests/:id} : Partial updates given fields of an existing diagnosticTest, field will ignore if it is null
     *
     * @param id the id of the diagnosticTestDTO to save.
     * @param diagnosticTestDTO the diagnosticTestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosticTestDTO,
     * or with status {@code 400 (Bad Request)} if the diagnosticTestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the diagnosticTestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the diagnosticTestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiagnosticTestDTO> partialUpdateDiagnosticTest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiagnosticTestDTO diagnosticTestDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DiagnosticTest partially : {}, {}", id, diagnosticTestDTO);
        if (diagnosticTestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosticTestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosticTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiagnosticTestDTO> result = diagnosticTestService.partialUpdate(diagnosticTestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosticTestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /diagnostic-tests} : get all the diagnosticTests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diagnosticTests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DiagnosticTestDTO>> getAllDiagnosticTests(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DiagnosticTests");
        Page<DiagnosticTestDTO> page = diagnosticTestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /diagnostic-tests/:id} : get the "id" diagnosticTest.
     *
     * @param id the id of the diagnosticTestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diagnosticTestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticTestDTO> getDiagnosticTest(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DiagnosticTest : {}", id);
        Optional<DiagnosticTestDTO> diagnosticTestDTO = diagnosticTestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diagnosticTestDTO);
    }

    /**
     * {@code DELETE  /diagnostic-tests/:id} : delete the "id" diagnosticTest.
     *
     * @param id the id of the diagnosticTestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnosticTest(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DiagnosticTest : {}", id);
        diagnosticTestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
