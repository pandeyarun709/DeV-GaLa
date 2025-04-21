package com.farmer.be.service.impl;

import com.farmer.be.domain.Qualification;
import com.farmer.be.repository.QualificationRepository;
import com.farmer.be.service.QualificationService;
import com.farmer.be.service.dto.QualificationDTO;
import com.farmer.be.service.mapper.QualificationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Qualification}.
 */
@Service
@Transactional
public class QualificationServiceImpl implements QualificationService {

    private static final Logger LOG = LoggerFactory.getLogger(QualificationServiceImpl.class);

    private final QualificationRepository qualificationRepository;

    private final QualificationMapper qualificationMapper;

    public QualificationServiceImpl(QualificationRepository qualificationRepository, QualificationMapper qualificationMapper) {
        this.qualificationRepository = qualificationRepository;
        this.qualificationMapper = qualificationMapper;
    }

    @Override
    public QualificationDTO save(QualificationDTO qualificationDTO) {
        LOG.debug("Request to save Qualification : {}", qualificationDTO);
        Qualification qualification = qualificationMapper.toEntity(qualificationDTO);
        qualification = qualificationRepository.save(qualification);
        return qualificationMapper.toDto(qualification);
    }

    @Override
    public QualificationDTO update(QualificationDTO qualificationDTO) {
        LOG.debug("Request to update Qualification : {}", qualificationDTO);
        Qualification qualification = qualificationMapper.toEntity(qualificationDTO);
        qualification = qualificationRepository.save(qualification);
        return qualificationMapper.toDto(qualification);
    }

    @Override
    public Optional<QualificationDTO> partialUpdate(QualificationDTO qualificationDTO) {
        LOG.debug("Request to partially update Qualification : {}", qualificationDTO);

        return qualificationRepository
            .findById(qualificationDTO.getId())
            .map(existingQualification -> {
                qualificationMapper.partialUpdate(existingQualification, qualificationDTO);

                return existingQualification;
            })
            .map(qualificationRepository::save)
            .map(qualificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QualificationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Qualifications");
        return qualificationRepository.findAll(pageable).map(qualificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QualificationDTO> findOne(Long id) {
        LOG.debug("Request to get Qualification : {}", id);
        return qualificationRepository.findById(id).map(qualificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Qualification : {}", id);
        qualificationRepository.deleteById(id);
    }
}
