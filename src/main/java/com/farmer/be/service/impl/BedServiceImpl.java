package com.farmer.be.service.impl;

import com.farmer.be.domain.Bed;
import com.farmer.be.repository.BedRepository;
import com.farmer.be.service.BedService;
import com.farmer.be.service.dto.BedDTO;
import com.farmer.be.service.mapper.BedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Bed}.
 */
@Service
@Transactional
public class BedServiceImpl implements BedService {

    private static final Logger LOG = LoggerFactory.getLogger(BedServiceImpl.class);

    private final BedRepository bedRepository;

    private final BedMapper bedMapper;

    public BedServiceImpl(BedRepository bedRepository, BedMapper bedMapper) {
        this.bedRepository = bedRepository;
        this.bedMapper = bedMapper;
    }

    @Override
    public BedDTO save(BedDTO bedDTO) {
        LOG.debug("Request to save Bed : {}", bedDTO);
        Bed bed = bedMapper.toEntity(bedDTO);
        bed = bedRepository.save(bed);
        return bedMapper.toDto(bed);
    }

    @Override
    public BedDTO update(BedDTO bedDTO) {
        LOG.debug("Request to update Bed : {}", bedDTO);
        Bed bed = bedMapper.toEntity(bedDTO);
        bed = bedRepository.save(bed);
        return bedMapper.toDto(bed);
    }

    @Override
    public Optional<BedDTO> partialUpdate(BedDTO bedDTO) {
        LOG.debug("Request to partially update Bed : {}", bedDTO);

        return bedRepository
            .findById(bedDTO.getId())
            .map(existingBed -> {
                bedMapper.partialUpdate(existingBed, bedDTO);

                return existingBed;
            })
            .map(bedRepository::save)
            .map(bedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BedDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Beds");
        return bedRepository.findAll(pageable).map(bedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BedDTO> findOne(Long id) {
        LOG.debug("Request to get Bed : {}", id);
        return bedRepository.findById(id).map(bedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Bed : {}", id);
        bedRepository.deleteById(id);
    }
}
