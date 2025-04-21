package com.farmer.be.service.impl;

import com.farmer.be.domain.ReferralDoctor;
import com.farmer.be.repository.ReferralDoctorRepository;
import com.farmer.be.service.ReferralDoctorService;
import com.farmer.be.service.dto.ReferralDoctorDTO;
import com.farmer.be.service.mapper.ReferralDoctorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.ReferralDoctor}.
 */
@Service
@Transactional
public class ReferralDoctorServiceImpl implements ReferralDoctorService {

    private static final Logger LOG = LoggerFactory.getLogger(ReferralDoctorServiceImpl.class);

    private final ReferralDoctorRepository referralDoctorRepository;

    private final ReferralDoctorMapper referralDoctorMapper;

    public ReferralDoctorServiceImpl(ReferralDoctorRepository referralDoctorRepository, ReferralDoctorMapper referralDoctorMapper) {
        this.referralDoctorRepository = referralDoctorRepository;
        this.referralDoctorMapper = referralDoctorMapper;
    }

    @Override
    public ReferralDoctorDTO save(ReferralDoctorDTO referralDoctorDTO) {
        LOG.debug("Request to save ReferralDoctor : {}", referralDoctorDTO);
        ReferralDoctor referralDoctor = referralDoctorMapper.toEntity(referralDoctorDTO);
        referralDoctor = referralDoctorRepository.save(referralDoctor);
        return referralDoctorMapper.toDto(referralDoctor);
    }

    @Override
    public ReferralDoctorDTO update(ReferralDoctorDTO referralDoctorDTO) {
        LOG.debug("Request to update ReferralDoctor : {}", referralDoctorDTO);
        ReferralDoctor referralDoctor = referralDoctorMapper.toEntity(referralDoctorDTO);
        referralDoctor = referralDoctorRepository.save(referralDoctor);
        return referralDoctorMapper.toDto(referralDoctor);
    }

    @Override
    public Optional<ReferralDoctorDTO> partialUpdate(ReferralDoctorDTO referralDoctorDTO) {
        LOG.debug("Request to partially update ReferralDoctor : {}", referralDoctorDTO);

        return referralDoctorRepository
            .findById(referralDoctorDTO.getId())
            .map(existingReferralDoctor -> {
                referralDoctorMapper.partialUpdate(existingReferralDoctor, referralDoctorDTO);

                return existingReferralDoctor;
            })
            .map(referralDoctorRepository::save)
            .map(referralDoctorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReferralDoctorDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ReferralDoctors");
        return referralDoctorRepository.findAll(pageable).map(referralDoctorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReferralDoctorDTO> findOne(Long id) {
        LOG.debug("Request to get ReferralDoctor : {}", id);
        return referralDoctorRepository.findById(id).map(referralDoctorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ReferralDoctor : {}", id);
        referralDoctorRepository.deleteById(id);
    }
}
