package com.farmer.be.service.impl;

import com.farmer.be.domain.Hospital;
import com.farmer.be.repository.HospitalRepository;
import com.farmer.be.service.HospitalService;
import com.farmer.be.service.dto.HospitalDTO;
import com.farmer.be.service.mapper.HospitalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Hospital}.
 */
@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {

    private static final Logger LOG = LoggerFactory.getLogger(HospitalServiceImpl.class);

    private final HospitalRepository hospitalRepository;

    private final HospitalMapper hospitalMapper;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
    }

    @Override
    public HospitalDTO save(HospitalDTO hospitalDTO) {
        LOG.debug("Request to save Hospital : {}", hospitalDTO);
        Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
        hospital = hospitalRepository.save(hospital);
        return hospitalMapper.toDto(hospital);
    }

    @Override
    public HospitalDTO update(HospitalDTO hospitalDTO) {
        LOG.debug("Request to update Hospital : {}", hospitalDTO);
        Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
        hospital = hospitalRepository.save(hospital);
        return hospitalMapper.toDto(hospital);
    }

    @Override
    public Optional<HospitalDTO> partialUpdate(HospitalDTO hospitalDTO) {
        LOG.debug("Request to partially update Hospital : {}", hospitalDTO);

        return hospitalRepository
            .findById(hospitalDTO.getId())
            .map(existingHospital -> {
                hospitalMapper.partialUpdate(existingHospital, hospitalDTO);

                return existingHospital;
            })
            .map(hospitalRepository::save)
            .map(hospitalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospitalDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Hospitals");
        return hospitalRepository.findAll(pageable).map(hospitalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HospitalDTO> findOne(Long id) {
        LOG.debug("Request to get Hospital : {}", id);
        return hospitalRepository.findById(id).map(hospitalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Hospital : {}", id);
        hospitalRepository.deleteById(id);
    }
}
