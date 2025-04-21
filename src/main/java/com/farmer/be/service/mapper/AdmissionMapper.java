package com.farmer.be.service.mapper;

import com.farmer.be.domain.Admission;
import com.farmer.be.domain.Bed;
import com.farmer.be.domain.Employee;
import com.farmer.be.domain.Hospital;
import com.farmer.be.domain.Patient;
import com.farmer.be.service.dto.AdmissionDTO;
import com.farmer.be.service.dto.BedDTO;
import com.farmer.be.service.dto.EmployeeDTO;
import com.farmer.be.service.dto.HospitalDTO;
import com.farmer.be.service.dto.PatientDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Admission} and its DTO {@link AdmissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdmissionMapper extends EntityMapper<AdmissionDTO, Admission> {
    @Mapping(target = "beds", source = "beds", qualifiedByName = "bedIdSet")
    @Mapping(target = "patient", source = "patient", qualifiedByName = "patientId")
    @Mapping(target = "hospital", source = "hospital", qualifiedByName = "hospitalId")
    @Mapping(target = "admittedUnder", source = "admittedUnder", qualifiedByName = "employeeId")
    AdmissionDTO toDto(Admission s);

    @Mapping(target = "removeBeds", ignore = true)
    Admission toEntity(AdmissionDTO admissionDTO);

    @Named("bedId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BedDTO toDtoBedId(Bed bed);

    @Named("bedIdSet")
    default Set<BedDTO> toDtoBedIdSet(Set<Bed> bed) {
        return bed.stream().map(this::toDtoBedId).collect(Collectors.toSet());
    }

    @Named("patientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoPatientId(Patient patient);

    @Named("hospitalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HospitalDTO toDtoHospitalId(Hospital hospital);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
