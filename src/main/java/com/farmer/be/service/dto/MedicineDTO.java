package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.MedicineType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.Medicine} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicineDTO implements Serializable {

    private Long id;

    private String name;

    private String genericMolecule;

    private Boolean isPrescriptionNeeded;

    private String description;

    private Double mrp;

    private Boolean isInsuranceCovered;

    private MedicineType type;

    private Boolean isConsumable;

    private Boolean isReturnable;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericMolecule() {
        return genericMolecule;
    }

    public void setGenericMolecule(String genericMolecule) {
        this.genericMolecule = genericMolecule;
    }

    public Boolean getIsPrescriptionNeeded() {
        return isPrescriptionNeeded;
    }

    public void setIsPrescriptionNeeded(Boolean isPrescriptionNeeded) {
        this.isPrescriptionNeeded = isPrescriptionNeeded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Boolean getIsInsuranceCovered() {
        return isInsuranceCovered;
    }

    public void setIsInsuranceCovered(Boolean isInsuranceCovered) {
        this.isInsuranceCovered = isInsuranceCovered;
    }

    public MedicineType getType() {
        return type;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }

    public Boolean getIsConsumable() {
        return isConsumable;
    }

    public void setIsConsumable(Boolean isConsumable) {
        this.isConsumable = isConsumable;
    }

    public Boolean getIsReturnable() {
        return isReturnable;
    }

    public void setIsReturnable(Boolean isReturnable) {
        this.isReturnable = isReturnable;
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
        if (!(o instanceof MedicineDTO)) {
            return false;
        }

        MedicineDTO medicineDTO = (MedicineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, medicineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", genericMolecule='" + getGenericMolecule() + "'" +
            ", isPrescriptionNeeded='" + getIsPrescriptionNeeded() + "'" +
            ", description='" + getDescription() + "'" +
            ", mrp=" + getMrp() +
            ", isInsuranceCovered='" + getIsInsuranceCovered() + "'" +
            ", type='" + getType() + "'" +
            ", isConsumable='" + getIsConsumable() + "'" +
            ", isReturnable='" + getIsReturnable() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
