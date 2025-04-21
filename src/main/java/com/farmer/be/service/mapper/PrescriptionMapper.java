package com.farmer.be.service.mapper;

import com.farmer.be.domain.Prescription;
import com.farmer.be.service.dto.PrescriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prescription} and its DTO {@link PrescriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrescriptionMapper extends EntityMapper<PrescriptionDTO, Prescription> {}
