package com.farmer.be.service.mapper;

import com.farmer.be.domain.Admission;
import com.farmer.be.domain.DiagnosticTest;
import com.farmer.be.domain.DiagnosticTestReport;
import com.farmer.be.domain.Patient;
import com.farmer.be.service.dto.AdmissionDTO;
import com.farmer.be.service.dto.DiagnosticTestDTO;
import com.farmer.be.service.dto.DiagnosticTestReportDTO;
import com.farmer.be.service.dto.PatientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiagnosticTestReport} and its DTO {@link DiagnosticTestReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiagnosticTestReportMapper extends EntityMapper<DiagnosticTestReportDTO, DiagnosticTestReport> {
    @Mapping(target = "patient", source = "patient", qualifiedByName = "patientId")
    @Mapping(target = "test", source = "test", qualifiedByName = "diagnosticTestId")
    @Mapping(target = "admissions", source = "admissions", qualifiedByName = "admissionId")
    DiagnosticTestReportDTO toDto(DiagnosticTestReport s);

    @Named("patientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoPatientId(Patient patient);

    @Named("diagnosticTestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DiagnosticTestDTO toDtoDiagnosticTestId(DiagnosticTest diagnosticTest);

    @Named("admissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdmissionDTO toDtoAdmissionId(Admission admission);
}
