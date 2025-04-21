package com.farmer.be.service.mapper;

import com.farmer.be.domain.Department;
import com.farmer.be.domain.DiagnosticTest;
import com.farmer.be.domain.Employee;
import com.farmer.be.domain.Qualification;
import com.farmer.be.service.dto.DepartmentDTO;
import com.farmer.be.service.dto.DiagnosticTestDTO;
import com.farmer.be.service.dto.EmployeeDTO;
import com.farmer.be.service.dto.QualificationDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "qualifications", source = "qualifications", qualifiedByName = "qualificationIdSet")
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentId")
    @Mapping(target = "test", source = "test", qualifiedByName = "diagnosticTestId")
    EmployeeDTO toDto(Employee s);

    @Mapping(target = "removeQualifications", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    @Named("qualificationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QualificationDTO toDtoQualificationId(Qualification qualification);

    @Named("qualificationIdSet")
    default Set<QualificationDTO> toDtoQualificationIdSet(Set<Qualification> qualification) {
        return qualification.stream().map(this::toDtoQualificationId).collect(Collectors.toSet());
    }

    @Named("departmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartmentDTO toDtoDepartmentId(Department department);

    @Named("diagnosticTestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DiagnosticTestDTO toDtoDiagnosticTestId(DiagnosticTest diagnosticTest);
}
