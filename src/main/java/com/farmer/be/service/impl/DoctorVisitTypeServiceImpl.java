package com.farmer.be.service.impl;

import com.farmer.be.domain.DoctorVisitType;
import com.farmer.be.repository.DoctorVisitTypeRepository;
import com.farmer.be.service.DoctorVisitTypeService;
import com.farmer.be.service.dto.DoctorVisitTypeDTO;
import com.farmer.be.service.mapper.DoctorVisitTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.DoctorVisitType}.
 */
@Service
@Transactional
public class DoctorVisitTypeServiceImpl implements DoctorVisitTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(DoctorVisitTypeServiceImpl.class);

    private final DoctorVisitTypeRepository doctorVisitTypeRepository;

    private final DoctorVisitTypeMapper doctorVisitTypeMapper;

    public DoctorVisitTypeServiceImpl(DoctorVisitTypeRepository doctorVisitTypeRepository, DoctorVisitTypeMapper doctorVisitTypeMapper) {
        this.doctorVisitTypeRepository = doctorVisitTypeRepository;
        this.doctorVisitTypeMapper = doctorVisitTypeMapper;
    }

    @Override
    public DoctorVisitTypeDTO save(DoctorVisitTypeDTO doctorVisitTypeDTO) {
        LOG.debug("Request to save DoctorVisitType : {}", doctorVisitTypeDTO);
        DoctorVisitType doctorVisitType = doctorVisitTypeMapper.toEntity(doctorVisitTypeDTO);
        doctorVisitType = doctorVisitTypeRepository.save(doctorVisitType);
        return doctorVisitTypeMapper.toDto(doctorVisitType);
    }

    @Override
    public DoctorVisitTypeDTO update(DoctorVisitTypeDTO doctorVisitTypeDTO) {
        LOG.debug("Request to update DoctorVisitType : {}", doctorVisitTypeDTO);
        DoctorVisitType doctorVisitType = doctorVisitTypeMapper.toEntity(doctorVisitTypeDTO);
        doctorVisitType = doctorVisitTypeRepository.save(doctorVisitType);
        return doctorVisitTypeMapper.toDto(doctorVisitType);
    }

    @Override
    public Optional<DoctorVisitTypeDTO> partialUpdate(DoctorVisitTypeDTO doctorVisitTypeDTO) {
        LOG.debug("Request to partially update DoctorVisitType : {}", doctorVisitTypeDTO);

        return doctorVisitTypeRepository
            .findById(doctorVisitTypeDTO.getId())
            .map(existingDoctorVisitType -> {
                doctorVisitTypeMapper.partialUpdate(existingDoctorVisitType, doctorVisitTypeDTO);

                return existingDoctorVisitType;
            })
            .map(doctorVisitTypeRepository::save)
            .map(doctorVisitTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorVisitTypeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DoctorVisitTypes");
        return doctorVisitTypeRepository.findAll(pageable).map(doctorVisitTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorVisitTypeDTO> findOne(Long id) {
        LOG.debug("Request to get DoctorVisitType : {}", id);
        return doctorVisitTypeRepository.findById(id).map(doctorVisitTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DoctorVisitType : {}", id);
        doctorVisitTypeRepository.deleteById(id);
    }
}
