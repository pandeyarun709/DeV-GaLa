package com.farmer.be.service.impl;

import com.farmer.be.domain.PatientRegistrationDetails;
import com.farmer.be.repository.PatientRegistrationDetailsRepository;
import com.farmer.be.service.PatientRegistrationDetailsService;
import com.farmer.be.service.dto.PatientRegistrationDetailsDTO;
import com.farmer.be.service.mapper.PatientRegistrationDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.PatientRegistrationDetails}.
 */
@Service
@Transactional
public class PatientRegistrationDetailsServiceImpl implements PatientRegistrationDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(PatientRegistrationDetailsServiceImpl.class);

    private final PatientRegistrationDetailsRepository patientRegistrationDetailsRepository;

    private final PatientRegistrationDetailsMapper patientRegistrationDetailsMapper;

    public PatientRegistrationDetailsServiceImpl(
        PatientRegistrationDetailsRepository patientRegistrationDetailsRepository,
        PatientRegistrationDetailsMapper patientRegistrationDetailsMapper
    ) {
        this.patientRegistrationDetailsRepository = patientRegistrationDetailsRepository;
        this.patientRegistrationDetailsMapper = patientRegistrationDetailsMapper;
    }

    @Override
    public PatientRegistrationDetailsDTO save(PatientRegistrationDetailsDTO patientRegistrationDetailsDTO) {
        LOG.debug("Request to save PatientRegistrationDetails : {}", patientRegistrationDetailsDTO);
        PatientRegistrationDetails patientRegistrationDetails = patientRegistrationDetailsMapper.toEntity(patientRegistrationDetailsDTO);
        patientRegistrationDetails = patientRegistrationDetailsRepository.save(patientRegistrationDetails);
        return patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);
    }

    @Override
    public PatientRegistrationDetailsDTO update(PatientRegistrationDetailsDTO patientRegistrationDetailsDTO) {
        LOG.debug("Request to update PatientRegistrationDetails : {}", patientRegistrationDetailsDTO);
        PatientRegistrationDetails patientRegistrationDetails = patientRegistrationDetailsMapper.toEntity(patientRegistrationDetailsDTO);
        patientRegistrationDetails = patientRegistrationDetailsRepository.save(patientRegistrationDetails);
        return patientRegistrationDetailsMapper.toDto(patientRegistrationDetails);
    }

    @Override
    public Optional<PatientRegistrationDetailsDTO> partialUpdate(PatientRegistrationDetailsDTO patientRegistrationDetailsDTO) {
        LOG.debug("Request to partially update PatientRegistrationDetails : {}", patientRegistrationDetailsDTO);

        return patientRegistrationDetailsRepository
            .findById(patientRegistrationDetailsDTO.getId())
            .map(existingPatientRegistrationDetails -> {
                patientRegistrationDetailsMapper.partialUpdate(existingPatientRegistrationDetails, patientRegistrationDetailsDTO);

                return existingPatientRegistrationDetails;
            })
            .map(patientRegistrationDetailsRepository::save)
            .map(patientRegistrationDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientRegistrationDetailsDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PatientRegistrationDetails");
        return patientRegistrationDetailsRepository.findAll(pageable).map(patientRegistrationDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientRegistrationDetailsDTO> findOne(Long id) {
        LOG.debug("Request to get PatientRegistrationDetails : {}", id);
        return patientRegistrationDetailsRepository.findById(id).map(patientRegistrationDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PatientRegistrationDetails : {}", id);
        patientRegistrationDetailsRepository.deleteById(id);
    }
}
