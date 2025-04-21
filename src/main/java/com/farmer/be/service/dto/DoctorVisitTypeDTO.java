package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.VisitType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.DoctorVisitType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorVisitTypeDTO implements Serializable {

    private Long id;

    private VisitType type;

    private Double fee;

    private Boolean isInsuranceCovered;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private EmployeeDTO doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisitType getType() {
        return type;
    }

    public void setType(VisitType type) {
        this.type = type;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getIsInsuranceCovered() {
        return isInsuranceCovered;
    }

    public void setIsInsuranceCovered(Boolean isInsuranceCovered) {
        this.isInsuranceCovered = isInsuranceCovered;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public EmployeeDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(EmployeeDTO doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorVisitTypeDTO)) {
            return false;
        }

        DoctorVisitTypeDTO doctorVisitTypeDTO = (DoctorVisitTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, doctorVisitTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorVisitTypeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", fee=" + getFee() +
            ", isInsuranceCovered='" + getIsInsuranceCovered() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", doctor=" + getDoctor() +
            "}";
    }
}
