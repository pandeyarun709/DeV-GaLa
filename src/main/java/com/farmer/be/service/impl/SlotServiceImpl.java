package com.farmer.be.service.impl;

import com.farmer.be.domain.Slot;
import com.farmer.be.repository.SlotRepository;
import com.farmer.be.service.SlotService;
import com.farmer.be.service.dto.SlotDTO;
import com.farmer.be.service.mapper.SlotMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Slot}.
 */
@Service
@Transactional
public class SlotServiceImpl implements SlotService {

    private static final Logger LOG = LoggerFactory.getLogger(SlotServiceImpl.class);

    private final SlotRepository slotRepository;

    private final SlotMapper slotMapper;

    public SlotServiceImpl(SlotRepository slotRepository, SlotMapper slotMapper) {
        this.slotRepository = slotRepository;
        this.slotMapper = slotMapper;
    }

    @Override
    public SlotDTO save(SlotDTO slotDTO) {
        LOG.debug("Request to save Slot : {}", slotDTO);
        Slot slot = slotMapper.toEntity(slotDTO);
        slot = slotRepository.save(slot);
        return slotMapper.toDto(slot);
    }

    @Override
    public SlotDTO update(SlotDTO slotDTO) {
        LOG.debug("Request to update Slot : {}", slotDTO);
        Slot slot = slotMapper.toEntity(slotDTO);
        slot = slotRepository.save(slot);
        return slotMapper.toDto(slot);
    }

    @Override
    public Optional<SlotDTO> partialUpdate(SlotDTO slotDTO) {
        LOG.debug("Request to partially update Slot : {}", slotDTO);

        return slotRepository
            .findById(slotDTO.getId())
            .map(existingSlot -> {
                slotMapper.partialUpdate(existingSlot, slotDTO);

                return existingSlot;
            })
            .map(slotRepository::save)
            .map(slotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SlotDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Slots");
        return slotRepository.findAll(pageable).map(slotMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SlotDTO> findOne(Long id) {
        LOG.debug("Request to get Slot : {}", id);
        return slotRepository.findById(id).map(slotMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Slot : {}", id);
        slotRepository.deleteById(id);
    }
}
