package com.farmer.be.service.mapper;

import com.farmer.be.domain.Address;
import com.farmer.be.domain.Client;
import com.farmer.be.domain.Hospital;
import com.farmer.be.service.dto.AddressDTO;
import com.farmer.be.service.dto.ClientDTO;
import com.farmer.be.service.dto.HospitalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hospital} and its DTO {@link HospitalDTO}.
 */
@Mapper(componentModel = "spring")
public interface HospitalMapper extends EntityMapper<HospitalDTO, Hospital> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    HospitalDTO toDto(Hospital s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
