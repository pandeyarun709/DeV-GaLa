package com.farmer.be.service.impl;

import com.farmer.be.domain.MedicineDose;
import com.farmer.be.repository.MedicineDoseRepository;
import com.farmer.be.service.MedicineDoseService;
import com.farmer.be.service.dto.MedicineDoseDTO;
import com.farmer.be.service.mapper.MedicineDoseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.MedicineDose}.
 */
@Service
@Transactional
public class MedicineDoseServiceImpl implements MedicineDoseService {

    private static final Logger LOG = LoggerFactory.getLogger(MedicineDoseServiceImpl.class);

    private final MedicineDoseRepository medicineDoseRepository;

    private final MedicineDoseMapper medicineDoseMapper;

    public MedicineDoseServiceImpl(MedicineDoseRepository medicineDoseRepository, MedicineDoseMapper medicineDoseMapper) {
        this.medicineDoseRepository = medicineDoseRepository;
        this.medicineDoseMapper = medicineDoseMapper;
    }

    @Override
    public MedicineDoseDTO save(MedicineDoseDTO medicineDoseDTO) {
        LOG.debug("Request to save MedicineDose : {}", medicineDoseDTO);
        MedicineDose medicineDose = medicineDoseMapper.toEntity(medicineDoseDTO);
        medicineDose = medicineDoseRepository.save(medicineDose);
        return medicineDoseMapper.toDto(medicineDose);
    }

    @Override
    public MedicineDoseDTO update(MedicineDoseDTO medicineDoseDTO) {
        LOG.debug("Request to update MedicineDose : {}", medicineDoseDTO);
        MedicineDose medicineDose = medicineDoseMapper.toEntity(medicineDoseDTO);
        medicineDose = medicineDoseRepository.save(medicineDose);
        return medicineDoseMapper.toDto(medicineDose);
    }

    @Override
    public Optional<MedicineDoseDTO> partialUpdate(MedicineDoseDTO medicineDoseDTO) {
        LOG.debug("Request to partially update MedicineDose : {}", medicineDoseDTO);

        return medicineDoseRepository
            .findById(medicineDoseDTO.getId())
            .map(existingMedicineDose -> {
                medicineDoseMapper.partialUpdate(existingMedicineDose, medicineDoseDTO);

                return existingMedicineDose;
            })
            .map(medicineDoseRepository::save)
            .map(medicineDoseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineDoseDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MedicineDoses");
        return medicineDoseRepository.findAll(pageable).map(medicineDoseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicineDoseDTO> findOne(Long id) {
        LOG.debug("Request to get MedicineDose : {}", id);
        return medicineDoseRepository.findById(id).map(medicineDoseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete MedicineDose : {}", id);
        medicineDoseRepository.deleteById(id);
    }
}
