package com.farmer.be.service.impl;

import com.farmer.be.domain.MedicineBatch;
import com.farmer.be.repository.MedicineBatchRepository;
import com.farmer.be.service.MedicineBatchService;
import com.farmer.be.service.dto.MedicineBatchDTO;
import com.farmer.be.service.mapper.MedicineBatchMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.MedicineBatch}.
 */
@Service
@Transactional
public class MedicineBatchServiceImpl implements MedicineBatchService {

    private static final Logger LOG = LoggerFactory.getLogger(MedicineBatchServiceImpl.class);

    private final MedicineBatchRepository medicineBatchRepository;

    private final MedicineBatchMapper medicineBatchMapper;

    public MedicineBatchServiceImpl(MedicineBatchRepository medicineBatchRepository, MedicineBatchMapper medicineBatchMapper) {
        this.medicineBatchRepository = medicineBatchRepository;
        this.medicineBatchMapper = medicineBatchMapper;
    }

    @Override
    public MedicineBatchDTO save(MedicineBatchDTO medicineBatchDTO) {
        LOG.debug("Request to save MedicineBatch : {}", medicineBatchDTO);
        MedicineBatch medicineBatch = medicineBatchMapper.toEntity(medicineBatchDTO);
        medicineBatch = medicineBatchRepository.save(medicineBatch);
        return medicineBatchMapper.toDto(medicineBatch);
    }

    @Override
    public MedicineBatchDTO update(MedicineBatchDTO medicineBatchDTO) {
        LOG.debug("Request to update MedicineBatch : {}", medicineBatchDTO);
        MedicineBatch medicineBatch = medicineBatchMapper.toEntity(medicineBatchDTO);
        medicineBatch = medicineBatchRepository.save(medicineBatch);
        return medicineBatchMapper.toDto(medicineBatch);
    }

    @Override
    public Optional<MedicineBatchDTO> partialUpdate(MedicineBatchDTO medicineBatchDTO) {
        LOG.debug("Request to partially update MedicineBatch : {}", medicineBatchDTO);

        return medicineBatchRepository
            .findById(medicineBatchDTO.getId())
            .map(existingMedicineBatch -> {
                medicineBatchMapper.partialUpdate(existingMedicineBatch, medicineBatchDTO);

                return existingMedicineBatch;
            })
            .map(medicineBatchRepository::save)
            .map(medicineBatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineBatchDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MedicineBatches");
        return medicineBatchRepository.findAll(pageable).map(medicineBatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicineBatchDTO> findOne(Long id) {
        LOG.debug("Request to get MedicineBatch : {}", id);
        return medicineBatchRepository.findById(id).map(medicineBatchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete MedicineBatch : {}", id);
        medicineBatchRepository.deleteById(id);
    }
}
