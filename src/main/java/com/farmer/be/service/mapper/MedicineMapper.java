package com.farmer.be.service.mapper;

import com.farmer.be.domain.Medicine;
import com.farmer.be.service.dto.MedicineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medicine} and its DTO {@link MedicineDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicineMapper extends EntityMapper<MedicineDTO, Medicine> {}
