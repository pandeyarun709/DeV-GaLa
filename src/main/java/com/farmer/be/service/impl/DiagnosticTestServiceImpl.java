package com.farmer.be.service.impl;

import com.farmer.be.domain.DiagnosticTest;
import com.farmer.be.repository.DiagnosticTestRepository;
import com.farmer.be.service.DiagnosticTestService;
import com.farmer.be.service.dto.DiagnosticTestDTO;
import com.farmer.be.service.mapper.DiagnosticTestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.DiagnosticTest}.
 */
@Service
@Transactional
public class DiagnosticTestServiceImpl implements DiagnosticTestService {

    private static final Logger LOG = LoggerFactory.getLogger(DiagnosticTestServiceImpl.class);

    private final DiagnosticTestRepository diagnosticTestRepository;

    private final DiagnosticTestMapper diagnosticTestMapper;

    public DiagnosticTestServiceImpl(DiagnosticTestRepository diagnosticTestRepository, DiagnosticTestMapper diagnosticTestMapper) {
        this.diagnosticTestRepository = diagnosticTestRepository;
        this.diagnosticTestMapper = diagnosticTestMapper;
    }

    @Override
    public DiagnosticTestDTO save(DiagnosticTestDTO diagnosticTestDTO) {
        LOG.debug("Request to save DiagnosticTest : {}", diagnosticTestDTO);
        DiagnosticTest diagnosticTest = diagnosticTestMapper.toEntity(diagnosticTestDTO);
        diagnosticTest = diagnosticTestRepository.save(diagnosticTest);
        return diagnosticTestMapper.toDto(diagnosticTest);
    }

    @Override
    public DiagnosticTestDTO update(DiagnosticTestDTO diagnosticTestDTO) {
        LOG.debug("Request to update DiagnosticTest : {}", diagnosticTestDTO);
        DiagnosticTest diagnosticTest = diagnosticTestMapper.toEntity(diagnosticTestDTO);
        diagnosticTest = diagnosticTestRepository.save(diagnosticTest);
        return diagnosticTestMapper.toDto(diagnosticTest);
    }

    @Override
    public Optional<DiagnosticTestDTO> partialUpdate(DiagnosticTestDTO diagnosticTestDTO) {
        LOG.debug("Request to partially update DiagnosticTest : {}", diagnosticTestDTO);

        return diagnosticTestRepository
            .findById(diagnosticTestDTO.getId())
            .map(existingDiagnosticTest -> {
                diagnosticTestMapper.partialUpdate(existingDiagnosticTest, diagnosticTestDTO);

                return existingDiagnosticTest;
            })
            .map(diagnosticTestRepository::save)
            .map(diagnosticTestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiagnosticTestDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DiagnosticTests");
        return diagnosticTestRepository.findAll(pageable).map(diagnosticTestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiagnosticTestDTO> findOne(Long id) {
        LOG.debug("Request to get DiagnosticTest : {}", id);
        return diagnosticTestRepository.findById(id).map(diagnosticTestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DiagnosticTest : {}", id);
        diagnosticTestRepository.deleteById(id);
    }
}
