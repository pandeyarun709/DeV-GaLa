package com.farmer.be.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.MedicineDose} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicineDoseDTO implements Serializable {

    private Long id;

    private String frequency;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private PrescriptionDTO prescription;

    private MedicineDTO medicine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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

    public PrescriptionDTO getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionDTO prescription) {
        this.prescription = prescription;
    }

    public MedicineDTO getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineDTO medicine) {
        this.medicine = medicine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicineDoseDTO)) {
            return false;
        }

        MedicineDoseDTO medicineDoseDTO = (MedicineDoseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, medicineDoseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicineDoseDTO{" +
            "id=" + getId() +
            ", frequency='" + getFrequency() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", prescription=" + getPrescription() +
            ", medicine=" + getMedicine() +
            "}";
    }
}
