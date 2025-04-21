package com.farmer.be.service.impl;

import com.farmer.be.domain.Medicine;
import com.farmer.be.repository.MedicineRepository;
import com.farmer.be.service.MedicineService;
import com.farmer.be.service.dto.MedicineDTO;
import com.farmer.be.service.mapper.MedicineMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Medicine}.
 */
@Service
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private static final Logger LOG = LoggerFactory.getLogger(MedicineServiceImpl.class);

    private final MedicineRepository medicineRepository;

    private final MedicineMapper medicineMapper;

    public MedicineServiceImpl(MedicineRepository medicineRepository, MedicineMapper medicineMapper) {
        this.medicineRepository = medicineRepository;
        this.medicineMapper = medicineMapper;
    }

    @Override
    public MedicineDTO save(MedicineDTO medicineDTO) {
        LOG.debug("Request to save Medicine : {}", medicineDTO);
        Medicine medicine = medicineMapper.toEntity(medicineDTO);
        medicine = medicineRepository.save(medicine);
        return medicineMapper.toDto(medicine);
    }

    @Override
    public MedicineDTO update(MedicineDTO medicineDTO) {
        LOG.debug("Request to update Medicine : {}", medicineDTO);
        Medicine medicine = medicineMapper.toEntity(medicineDTO);
        medicine = medicineRepository.save(medicine);
        return medicineMapper.toDto(medicine);
    }

    @Override
    public Optional<MedicineDTO> partialUpdate(MedicineDTO medicineDTO) {
        LOG.debug("Request to partially update Medicine : {}", medicineDTO);

        return medicineRepository
            .findById(medicineDTO.getId())
            .map(existingMedicine -> {
                medicineMapper.partialUpdate(existingMedicine, medicineDTO);

                return existingMedicine;
            })
            .map(medicineRepository::save)
            .map(medicineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicineDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Medicines");
        return medicineRepository.findAll(pageable).map(medicineMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicineDTO> findOne(Long id) {
        LOG.debug("Request to get Medicine : {}", id);
        return medicineRepository.findById(id).map(medicineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Medicine : {}", id);
        medicineRepository.deleteById(id);
    }
}
