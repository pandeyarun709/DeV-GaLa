package com.farmer.be.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.Prescription} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrescriptionDTO implements Serializable {

    private Long id;

    private String history;

    private String compliant;

    private Double height;

    private Double weight;

    private Double bpHigh;

    private Double bpLow;

    private Double temperature;

    private String otherVital;

    private String description;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getCompliant() {
        return compliant;
    }

    public void setCompliant(String compliant) {
        this.compliant = compliant;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBpHigh() {
        return bpHigh;
    }

    public void setBpHigh(Double bpHigh) {
        this.bpHigh = bpHigh;
    }

    public Double getBpLow() {
        return bpLow;
    }

    public void setBpLow(Double bpLow) {
        this.bpLow = bpLow;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getOtherVital() {
        return otherVital;
    }

    public void setOtherVital(String otherVital) {
        this.otherVital = otherVital;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescriptionDTO)) {
            return false;
        }

        PrescriptionDTO prescriptionDTO = (PrescriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prescriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrescriptionDTO{" +
            "id=" + getId() +
            ", history='" + getHistory() + "'" +
            ", compliant='" + getCompliant() + "'" +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", bpHigh=" + getBpHigh() +
            ", bpLow=" + getBpLow() +
            ", temperature=" + getTemperature() +
            ", otherVital='" + getOtherVital() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
