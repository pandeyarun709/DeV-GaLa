package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.BedType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.farmer.be.domain.Bed} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BedDTO implements Serializable {

    private Long id;

    private BedType type;

    private Long floor;

    private Long roomNo;

    private Double dailyRate;

    private Boolean isInsuranceCovered;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private HospitalDTO hospital;

    private Set<AdmissionDTO> admissions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BedType getType() {
        return type;
    }

    public void setType(BedType type) {
        this.type = type;
    }

    public Long getFloor() {
        return floor;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    public Long getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }

    public Double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
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

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public Set<AdmissionDTO> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(Set<AdmissionDTO> admissions) {
        this.admissions = admissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BedDTO)) {
            return false;
        }

        BedDTO bedDTO = (BedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BedDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", floor=" + getFloor() +
            ", roomNo=" + getRoomNo() +
            ", dailyRate=" + getDailyRate() +
            ", isInsuranceCovered='" + getIsInsuranceCovered() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", hospital=" + getHospital() +
            ", admissions=" + getAdmissions() +
            "}";
    }
}
