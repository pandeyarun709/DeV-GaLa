package com.farmer.be.service.mapper;

import com.farmer.be.domain.DoctorVisitType;
import com.farmer.be.domain.Employee;
import com.farmer.be.service.dto.DoctorVisitTypeDTO;
import com.farmer.be.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DoctorVisitType} and its DTO {@link DoctorVisitTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DoctorVisitTypeMapper extends EntityMapper<DoctorVisitTypeDTO, DoctorVisitType> {
    @Mapping(target = "doctor", source = "doctor", qualifiedByName = "employeeId")
    DoctorVisitTypeDTO toDto(DoctorVisitType s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
