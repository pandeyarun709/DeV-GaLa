package com.farmer.be.service.mapper;

import com.farmer.be.domain.Medicine;
import com.farmer.be.domain.MedicineDose;
import com.farmer.be.domain.Prescription;
import com.farmer.be.service.dto.MedicineDTO;
import com.farmer.be.service.dto.MedicineDoseDTO;
import com.farmer.be.service.dto.PrescriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicineDose} and its DTO {@link MedicineDoseDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicineDoseMapper extends EntityMapper<MedicineDoseDTO, MedicineDose> {
    @Mapping(target = "prescription", source = "prescription", qualifiedByName = "prescriptionId")
    @Mapping(target = "medicine", source = "medicine", qualifiedByName = "medicineId")
    MedicineDoseDTO toDto(MedicineDose s);

    @Named("prescriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PrescriptionDTO toDtoPrescriptionId(Prescription prescription);

    @Named("medicineId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MedicineDTO toDtoMedicineId(Medicine medicine);
}
