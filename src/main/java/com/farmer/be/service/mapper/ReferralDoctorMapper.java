package com.farmer.be.service.mapper;

import com.farmer.be.domain.ReferralDoctor;
import com.farmer.be.service.dto.ReferralDoctorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferralDoctor} and its DTO {@link ReferralDoctorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReferralDoctorMapper extends EntityMapper<ReferralDoctorDTO, ReferralDoctor> {}
