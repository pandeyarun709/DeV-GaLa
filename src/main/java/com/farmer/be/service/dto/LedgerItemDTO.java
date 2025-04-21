package com.farmer.be.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.LedgerItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LedgerItemDTO implements Serializable {

    private Long id;

    private Instant time;

    private String description;

    private Double fee;

    private Boolean isCoveredByInsurance;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private AdmissionDTO admission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getIsCoveredByInsurance() {
        return isCoveredByInsurance;
    }

    public void setIsCoveredByInsurance(Boolean isCoveredByInsurance) {
        this.isCoveredByInsurance = isCoveredByInsurance;
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

    public AdmissionDTO getAdmission() {
        return admission;
    }

    public void setAdmission(AdmissionDTO admission) {
        this.admission = admission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LedgerItemDTO)) {
            return false;
        }

        LedgerItemDTO ledgerItemDTO = (LedgerItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ledgerItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LedgerItemDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", description='" + getDescription() + "'" +
            ", fee=" + getFee() +
            ", isCoveredByInsurance='" + getIsCoveredByInsurance() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", admission=" + getAdmission() +
            "}";
    }
}
