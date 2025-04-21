package com.farmer.be.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.PatientRegistrationDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientRegistrationDetailsDTO implements Serializable {

    private Long id;

    private String registrationId;

    private ClientDTO client;

    private HospitalDTO hospital;

    private ReferralDoctorDTO referredBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public ReferralDoctorDTO getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(ReferralDoctorDTO referredBy) {
        this.referredBy = referredBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientRegistrationDetailsDTO)) {
            return false;
        }

        PatientRegistrationDetailsDTO patientRegistrationDetailsDTO = (PatientRegistrationDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientRegistrationDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientRegistrationDetailsDTO{" +
            "id=" + getId() +
            ", registrationId='" + getRegistrationId() + "'" +
            ", client=" + getClient() +
            ", hospital=" + getHospital() +
            ", referredBy=" + getReferredBy() +
            "}";
    }
}
