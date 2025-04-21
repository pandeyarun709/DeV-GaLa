package com.farmer.be.service.impl;

import com.farmer.be.domain.DoctorVisit;
import com.farmer.be.repository.DoctorVisitRepository;
import com.farmer.be.service.DoctorVisitService;
import com.farmer.be.service.dto.DoctorVisitDTO;
import com.farmer.be.service.mapper.DoctorVisitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.DoctorVisit}.
 */
@Service
@Transactional
public class DoctorVisitServiceImpl implements DoctorVisitService {

    private static final Logger LOG = LoggerFactory.getLogger(DoctorVisitServiceImpl.class);

    private final DoctorVisitRepository doctorVisitRepository;

    private final DoctorVisitMapper doctorVisitMapper;

    public DoctorVisitServiceImpl(DoctorVisitRepository doctorVisitRepository, DoctorVisitMapper doctorVisitMapper) {
        this.doctorVisitRepository = doctorVisitRepository;
        this.doctorVisitMapper = doctorVisitMapper;
    }

    @Override
    public DoctorVisitDTO save(DoctorVisitDTO doctorVisitDTO) {
        LOG.debug("Request to save DoctorVisit : {}", doctorVisitDTO);
        DoctorVisit doctorVisit = doctorVisitMapper.toEntity(doctorVisitDTO);
        doctorVisit = doctorVisitRepository.save(doctorVisit);
        return doctorVisitMapper.toDto(doctorVisit);
    }

    @Override
    public DoctorVisitDTO update(DoctorVisitDTO doctorVisitDTO) {
        LOG.debug("Request to update DoctorVisit : {}", doctorVisitDTO);
        DoctorVisit doctorVisit = doctorVisitMapper.toEntity(doctorVisitDTO);
        doctorVisit = doctorVisitRepository.save(doctorVisit);
        return doctorVisitMapper.toDto(doctorVisit);
    }

    @Override
    public Optional<DoctorVisitDTO> partialUpdate(DoctorVisitDTO doctorVisitDTO) {
        LOG.debug("Request to partially update DoctorVisit : {}", doctorVisitDTO);

        return doctorVisitRepository
            .findById(doctorVisitDTO.getId())
            .map(existingDoctorVisit -> {
                doctorVisitMapper.partialUpdate(existingDoctorVisit, doctorVisitDTO);

                return existingDoctorVisit;
            })
            .map(doctorVisitRepository::save)
            .map(doctorVisitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorVisitDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DoctorVisits");
        return doctorVisitRepository.findAll(pageable).map(doctorVisitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorVisitDTO> findOne(Long id) {
        LOG.debug("Request to get DoctorVisit : {}", id);
        return doctorVisitRepository.findById(id).map(doctorVisitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DoctorVisit : {}", id);
        doctorVisitRepository.deleteById(id);
    }
}
