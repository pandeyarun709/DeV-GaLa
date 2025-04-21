package com.farmer.be.service.impl;

import com.farmer.be.domain.LedgerItem;
import com.farmer.be.repository.LedgerItemRepository;
import com.farmer.be.service.LedgerItemService;
import com.farmer.be.service.dto.LedgerItemDTO;
import com.farmer.be.service.mapper.LedgerItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.LedgerItem}.
 */
@Service
@Transactional
public class LedgerItemServiceImpl implements LedgerItemService {

    private static final Logger LOG = LoggerFactory.getLogger(LedgerItemServiceImpl.class);

    private final LedgerItemRepository ledgerItemRepository;

    private final LedgerItemMapper ledgerItemMapper;

    public LedgerItemServiceImpl(LedgerItemRepository ledgerItemRepository, LedgerItemMapper ledgerItemMapper) {
        this.ledgerItemRepository = ledgerItemRepository;
        this.ledgerItemMapper = ledgerItemMapper;
    }

    @Override
    public LedgerItemDTO save(LedgerItemDTO ledgerItemDTO) {
        LOG.debug("Request to save LedgerItem : {}", ledgerItemDTO);
        LedgerItem ledgerItem = ledgerItemMapper.toEntity(ledgerItemDTO);
        ledgerItem = ledgerItemRepository.save(ledgerItem);
        return ledgerItemMapper.toDto(ledgerItem);
    }

    @Override
    public LedgerItemDTO update(LedgerItemDTO ledgerItemDTO) {
        LOG.debug("Request to update LedgerItem : {}", ledgerItemDTO);
        LedgerItem ledgerItem = ledgerItemMapper.toEntity(ledgerItemDTO);
        ledgerItem = ledgerItemRepository.save(ledgerItem);
        return ledgerItemMapper.toDto(ledgerItem);
    }

    @Override
    public Optional<LedgerItemDTO> partialUpdate(LedgerItemDTO ledgerItemDTO) {
        LOG.debug("Request to partially update LedgerItem : {}", ledgerItemDTO);

        return ledgerItemRepository
            .findById(ledgerItemDTO.getId())
            .map(existingLedgerItem -> {
                ledgerItemMapper.partialUpdate(existingLedgerItem, ledgerItemDTO);

                return existingLedgerItem;
            })
            .map(ledgerItemRepository::save)
            .map(ledgerItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LedgerItemDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all LedgerItems");
        return ledgerItemRepository.findAll(pageable).map(ledgerItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LedgerItemDTO> findOne(Long id) {
        LOG.debug("Request to get LedgerItem : {}", id);
        return ledgerItemRepository.findById(id).map(ledgerItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete LedgerItem : {}", id);
        ledgerItemRepository.deleteById(id);
    }
}
