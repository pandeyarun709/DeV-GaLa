package com.farmer.be.service.mapper;

import com.farmer.be.domain.Admission;
import com.farmer.be.domain.Hospital;
import com.farmer.be.domain.Medicine;
import com.farmer.be.domain.MedicineBatch;
import com.farmer.be.service.dto.AdmissionDTO;
import com.farmer.be.service.dto.HospitalDTO;
import com.farmer.be.service.dto.MedicineBatchDTO;
import com.farmer.be.service.dto.MedicineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicineBatch} and its DTO {@link MedicineBatchDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicineBatchMapper extends EntityMapper<MedicineBatchDTO, MedicineBatch> {
    @Mapping(target = "med", source = "med", qualifiedByName = "medicineId")
    @Mapping(target = "hospital", source = "hospital", qualifiedByName = "hospitalId")
    @Mapping(target = "admissions", source = "admissions", qualifiedByName = "admissionId")
    MedicineBatchDTO toDto(MedicineBatch s);

    @Named("medicineId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MedicineDTO toDtoMedicineId(Medicine medicine);

    @Named("hospitalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HospitalDTO toDtoHospitalId(Hospital hospital);

    @Named("admissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdmissionDTO toDtoAdmissionId(Admission admission);
}
