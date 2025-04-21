package com.farmer.be.service.mapper;

import com.farmer.be.domain.Admission;
import com.farmer.be.domain.Bed;
import com.farmer.be.domain.Hospital;
import com.farmer.be.service.dto.AdmissionDTO;
import com.farmer.be.service.dto.BedDTO;
import com.farmer.be.service.dto.HospitalDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bed} and its DTO {@link BedDTO}.
 */
@Mapper(componentModel = "spring")
public interface BedMapper extends EntityMapper<BedDTO, Bed> {
    @Mapping(target = "hospital", source = "hospital", qualifiedByName = "hospitalId")
    @Mapping(target = "admissions", source = "admissions", qualifiedByName = "admissionIdSet")
    BedDTO toDto(Bed s);

    @Mapping(target = "admissions", ignore = true)
    @Mapping(target = "removeAdmissions", ignore = true)
    Bed toEntity(BedDTO bedDTO);

    @Named("hospitalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HospitalDTO toDtoHospitalId(Hospital hospital);

    @Named("admissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdmissionDTO toDtoAdmissionId(Admission admission);

    @Named("admissionIdSet")
    default Set<AdmissionDTO> toDtoAdmissionIdSet(Set<Admission> admission) {
        return admission.stream().map(this::toDtoAdmissionId).collect(Collectors.toSet());
    }
}
