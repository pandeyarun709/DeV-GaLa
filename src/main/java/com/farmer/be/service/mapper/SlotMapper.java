package com.farmer.be.service.mapper;

import com.farmer.be.domain.DiagnosticTest;
import com.farmer.be.domain.DoctorVisitType;
import com.farmer.be.domain.Slot;
import com.farmer.be.service.dto.DiagnosticTestDTO;
import com.farmer.be.service.dto.DoctorVisitTypeDTO;
import com.farmer.be.service.dto.SlotDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Slot} and its DTO {@link SlotDTO}.
 */
@Mapper(componentModel = "spring")
public interface SlotMapper extends EntityMapper<SlotDTO, Slot> {
    @Mapping(target = "doctorVisitType", source = "doctorVisitType", qualifiedByName = "doctorVisitTypeId")
    @Mapping(target = "test", source = "test", qualifiedByName = "diagnosticTestId")
    SlotDTO toDto(Slot s);

    @Named("doctorVisitTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DoctorVisitTypeDTO toDtoDoctorVisitTypeId(DoctorVisitType doctorVisitType);

    @Named("diagnosticTestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DiagnosticTestDTO toDtoDiagnosticTestId(DiagnosticTest diagnosticTest);
}
