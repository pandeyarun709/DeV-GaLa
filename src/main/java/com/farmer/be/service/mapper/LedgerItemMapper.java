package com.farmer.be.service.mapper;

import com.farmer.be.domain.Admission;
import com.farmer.be.domain.LedgerItem;
import com.farmer.be.service.dto.AdmissionDTO;
import com.farmer.be.service.dto.LedgerItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LedgerItem} and its DTO {@link LedgerItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface LedgerItemMapper extends EntityMapper<LedgerItemDTO, LedgerItem> {
    @Mapping(target = "admission", source = "admission", qualifiedByName = "admissionId")
    LedgerItemDTO toDto(LedgerItem s);

    @Named("admissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdmissionDTO toDtoAdmissionId(Admission admission);
}
