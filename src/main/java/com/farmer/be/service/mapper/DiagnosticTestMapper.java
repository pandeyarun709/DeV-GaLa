package com.farmer.be.service.mapper;

import com.farmer.be.domain.Department;
import com.farmer.be.domain.DiagnosticTest;
import com.farmer.be.domain.Prescription;
import com.farmer.be.service.dto.DepartmentDTO;
import com.farmer.be.service.dto.DiagnosticTestDTO;
import com.farmer.be.service.dto.PrescriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiagnosticTest} and its DTO {@link DiagnosticTestDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiagnosticTestMapper extends EntityMapper<DiagnosticTestDTO, DiagnosticTest> {
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentId")
    @Mapping(target = "prescription", source = "prescription", qualifiedByName = "prescriptionId")
    DiagnosticTestDTO toDto(DiagnosticTest s);

    @Named("departmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartmentDTO toDtoDepartmentId(Department department);

    @Named("prescriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PrescriptionDTO toDtoPrescriptionId(Prescription prescription);
}
