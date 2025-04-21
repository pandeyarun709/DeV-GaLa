package com.farmer.be.service.mapper;

import com.farmer.be.domain.Employee;
import com.farmer.be.domain.Qualification;
import com.farmer.be.service.dto.EmployeeDTO;
import com.farmer.be.service.dto.QualificationDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Qualification} and its DTO {@link QualificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface QualificationMapper extends EntityMapper<QualificationDTO, Qualification> {
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeeIdSet")
    QualificationDTO toDto(Qualification s);

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "removeEmployees", ignore = true)
    Qualification toEntity(QualificationDTO qualificationDTO);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("employeeIdSet")
    default Set<EmployeeDTO> toDtoEmployeeIdSet(Set<Employee> employee) {
        return employee.stream().map(this::toDtoEmployeeId).collect(Collectors.toSet());
    }
}
