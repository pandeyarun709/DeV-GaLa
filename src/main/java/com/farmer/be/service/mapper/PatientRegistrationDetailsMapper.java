package com.farmer.be.service.mapper;

import com.farmer.be.domain.Client;
import com.farmer.be.domain.Hospital;
import com.farmer.be.domain.PatientRegistrationDetails;
import com.farmer.be.domain.ReferralDoctor;
import com.farmer.be.service.dto.ClientDTO;
import com.farmer.be.service.dto.HospitalDTO;
import com.farmer.be.service.dto.PatientRegistrationDetailsDTO;
import com.farmer.be.service.dto.ReferralDoctorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientRegistrationDetails} and its DTO {@link PatientRegistrationDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientRegistrationDetailsMapper extends EntityMapper<PatientRegistrationDetailsDTO, PatientRegistrationDetails> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "hospital", source = "hospital", qualifiedByName = "hospitalId")
    @Mapping(target = "referredBy", source = "referredBy", qualifiedByName = "referralDoctorId")
    PatientRegistrationDetailsDTO toDto(PatientRegistrationDetails s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("hospitalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HospitalDTO toDtoHospitalId(Hospital hospital);

    @Named("referralDoctorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReferralDoctorDTO toDtoReferralDoctorId(ReferralDoctor referralDoctor);
}
