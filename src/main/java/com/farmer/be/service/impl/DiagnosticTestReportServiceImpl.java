package com.farmer.be.service.impl;

import com.farmer.be.domain.DiagnosticTestReport;
import com.farmer.be.repository.DiagnosticTestReportRepository;
import com.farmer.be.service.DiagnosticTestReportService;
import com.farmer.be.service.dto.DiagnosticTestReportDTO;
import com.farmer.be.service.mapper.DiagnosticTestReportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.DiagnosticTestReport}.
 */
@Service
@Transactional
public class DiagnosticTestReportServiceImpl implements DiagnosticTestReportService {

    private static final Logger LOG = LoggerFactory.getLogger(DiagnosticTestReportServiceImpl.class);

    private final DiagnosticTestReportRepository diagnosticTestReportRepository;

    private final DiagnosticTestReportMapper diagnosticTestReportMapper;

    public DiagnosticTestReportServiceImpl(
        DiagnosticTestReportRepository diagnosticTestReportRepository,
        DiagnosticTestReportMapper diagnosticTestReportMapper
    ) {
        this.diagnosticTestReportRepository = diagnosticTestReportRepository;
        this.diagnosticTestReportMapper = diagnosticTestReportMapper;
    }

    @Override
    public DiagnosticTestReportDTO save(DiagnosticTestReportDTO diagnosticTestReportDTO) {
        LOG.debug("Request to save DiagnosticTestReport : {}", diagnosticTestReportDTO);
        DiagnosticTestReport diagnosticTestReport = diagnosticTestReportMapper.toEntity(diagnosticTestReportDTO);
        diagnosticTestReport = diagnosticTestReportRepository.save(diagnosticTestReport);
        return diagnosticTestReportMapper.toDto(diagnosticTestReport);
    }

    @Override
    public DiagnosticTestReportDTO update(DiagnosticTestReportDTO diagnosticTestReportDTO) {
        LOG.debug("Request to update DiagnosticTestReport : {}", diagnosticTestReportDTO);
        DiagnosticTestReport diagnosticTestReport = diagnosticTestReportMapper.toEntity(diagnosticTestReportDTO);
        diagnosticTestReport = diagnosticTestReportRepository.save(diagnosticTestReport);
        return diagnosticTestReportMapper.toDto(diagnosticTestReport);
    }

    @Override
    public Optional<DiagnosticTestReportDTO> partialUpdate(DiagnosticTestReportDTO diagnosticTestReportDTO) {
        LOG.debug("Request to partially update DiagnosticTestReport : {}", diagnosticTestReportDTO);

        return diagnosticTestReportRepository
            .findById(diagnosticTestReportDTO.getId())
            .map(existingDiagnosticTestReport -> {
                diagnosticTestReportMapper.partialUpdate(existingDiagnosticTestReport, diagnosticTestReportDTO);

                return existingDiagnosticTestReport;
            })
            .map(diagnosticTestReportRepository::save)
            .map(diagnosticTestReportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiagnosticTestReportDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DiagnosticTestReports");
        return diagnosticTestReportRepository.findAll(pageable).map(diagnosticTestReportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiagnosticTestReportDTO> findOne(Long id) {
        LOG.debug("Request to get DiagnosticTestReport : {}", id);
        return diagnosticTestReportRepository.findById(id).map(diagnosticTestReportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DiagnosticTestReport : {}", id);
        diagnosticTestReportRepository.deleteById(id);
    }
}
