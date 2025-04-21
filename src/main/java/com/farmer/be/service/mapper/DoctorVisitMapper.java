package com.farmer.be.service.mapper;

import com.farmer.be.domain.Admission;
import com.farmer.be.domain.DoctorVisit;
import com.farmer.be.domain.DoctorVisitType;
import com.farmer.be.domain.Patient;
import com.farmer.be.domain.Prescription;
import com.farmer.be.service.dto.AdmissionDTO;
import com.farmer.be.service.dto.DoctorVisitDTO;
import com.farmer.be.service.dto.DoctorVisitTypeDTO;
import com.farmer.be.service.dto.PatientDTO;
import com.farmer.be.service.dto.PrescriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DoctorVisit} and its DTO {@link DoctorVisitDTO}.
 */
@Mapper(componentModel = "spring")
public interface DoctorVisitMapper extends EntityMapper<DoctorVisitDTO, DoctorVisit> {
    @Mapping(target = "prescription", source = "prescription", qualifiedByName = "prescriptionId")
    @Mapping(target = "doctorVisitType", source = "doctorVisitType", qualifiedByName = "doctorVisitTypeId")
    @Mapping(target = "patient", source = "patient", qualifiedByName = "patientId")
    @Mapping(target = "admissions", source = "admissions", qualifiedByName = "admissionId")
    DoctorVisitDTO toDto(DoctorVisit s);

    @Named("prescriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PrescriptionDTO toDtoPrescriptionId(Prescription prescription);

    @Named("doctorVisitTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DoctorVisitTypeDTO toDtoDoctorVisitTypeId(DoctorVisitType doctorVisitType);

    @Named("patientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoPatientId(Patient patient);

    @Named("admissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdmissionDTO toDtoAdmissionId(Admission admission);
}
