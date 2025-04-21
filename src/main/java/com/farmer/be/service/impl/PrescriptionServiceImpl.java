package com.farmer.be.service.impl;

import com.farmer.be.domain.Prescription;
import com.farmer.be.repository.PrescriptionRepository;
import com.farmer.be.service.PrescriptionService;
import com.farmer.be.service.dto.PrescriptionDTO;
import com.farmer.be.service.mapper.PrescriptionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Prescription}.
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private final PrescriptionRepository prescriptionRepository;

    private final PrescriptionMapper prescriptionMapper;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, PrescriptionMapper prescriptionMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
    }

    @Override
    public PrescriptionDTO save(PrescriptionDTO prescriptionDTO) {
        LOG.debug("Request to save Prescription : {}", prescriptionDTO);
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toDto(prescription);
    }

    @Override
    public PrescriptionDTO update(PrescriptionDTO prescriptionDTO) {
        LOG.debug("Request to update Prescription : {}", prescriptionDTO);
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toDto(prescription);
    }

    @Override
    public Optional<PrescriptionDTO> partialUpdate(PrescriptionDTO prescriptionDTO) {
        LOG.debug("Request to partially update Prescription : {}", prescriptionDTO);

        return prescriptionRepository
            .findById(prescriptionDTO.getId())
            .map(existingPrescription -> {
                prescriptionMapper.partialUpdate(existingPrescription, prescriptionDTO);

                return existingPrescription;
            })
            .map(prescriptionRepository::save)
            .map(prescriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll(pageable).map(prescriptionMapper::toDto);
    }

    /**
     *  Get all the prescriptions where DoctorVisitType is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrescriptionDTO> findAllWhereDoctorVisitTypeIsNull() {
        LOG.debug("Request to get all prescriptions where DoctorVisitType is null");
        return StreamSupport.stream(prescriptionRepository.findAll().spliterator(), false)
            .filter(prescription -> prescription.getDoctorVisitType() == null)
            .map(prescriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrescriptionDTO> findOne(Long id) {
        LOG.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findById(id).map(prescriptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Prescription : {}", id);
        prescriptionRepository.deleteById(id);
    }
}
