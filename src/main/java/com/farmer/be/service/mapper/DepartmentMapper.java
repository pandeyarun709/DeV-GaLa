package com.farmer.be.service.mapper;

import com.farmer.be.domain.Department;
import com.farmer.be.domain.Hospital;
import com.farmer.be.service.dto.DepartmentDTO;
import com.farmer.be.service.dto.HospitalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Mapping(target = "hospital", source = "hospital", qualifiedByName = "hospitalId")
    DepartmentDTO toDto(Department s);

    @Named("hospitalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HospitalDTO toDtoHospitalId(Hospital hospital);
}
