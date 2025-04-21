package com.farmer.be.service.mapper;

import com.farmer.be.domain.Address;
import com.farmer.be.domain.Patient;
import com.farmer.be.service.dto.AddressDTO;
import com.farmer.be.service.dto.PatientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    PatientDTO toDto(Patient s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
