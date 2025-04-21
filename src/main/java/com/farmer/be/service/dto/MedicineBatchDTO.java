package com.farmer.be.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.MedicineBatch} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicineBatchDTO implements Serializable {

    private Long id;

    private String batchNo;

    private LocalDate expiryDate;

    private Long quantity;

    private Double sellingPrice;

    private String storageLocation;

    private String rackNo;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private MedicineDTO med;

    private HospitalDTO hospital;

    private AdmissionDTO admissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getRackNo() {
        return rackNo;
    }

    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
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

    public MedicineDTO getMed() {
        return med;
    }

    public void setMed(MedicineDTO med) {
        this.med = med;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public AdmissionDTO getAdmissions() {
        return admissions;
    }

    public void setAdmissions(AdmissionDTO admissions) {
        this.admissions = admissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicineBatchDTO)) {
            return false;
        }

        MedicineBatchDTO medicineBatchDTO = (MedicineBatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, medicineBatchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicineBatchDTO{" +
            "id=" + getId() +
            ", batchNo='" + getBatchNo() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", quantity=" + getQuantity() +
            ", sellingPrice=" + getSellingPrice() +
            ", storageLocation='" + getStorageLocation() + "'" +
            ", rackNo='" + getRackNo() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", med=" + getMed() +
            ", hospital=" + getHospital() +
            ", admissions=" + getAdmissions() +
            "}";
    }
}
