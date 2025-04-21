package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MedicineBatch.
 */
@Entity
@Table(name = "medicine_batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicineBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "batch_no")
    private String batchNo;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "rack_no")
    private String rackNo;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "medicineDoses", "medicineBatches" }, allowSetters = true)
    private Medicine med;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "address", "patientRegistrationDetails", "departments", "beds", "medicineBatches", "admissions", "client" },
        allowSetters = true
    )
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "medicineBatches", "diagnosticTestReports", "doctorVisits", "ledgerItems", "beds", "patient", "hospital", "admittedUnder",
        },
        allowSetters = true
    )
    private Admission admissions;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MedicineBatch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public MedicineBatch batchNo(String batchNo) {
        this.setBatchNo(batchNo);
        return this;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    public MedicineBatch expiryDate(LocalDate expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public MedicineBatch quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getSellingPrice() {
        return this.sellingPrice;
    }

    public MedicineBatch sellingPrice(Double sellingPrice) {
        this.setSellingPrice(sellingPrice);
        return this;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getStorageLocation() {
        return this.storageLocation;
    }

    public MedicineBatch storageLocation(String storageLocation) {
        this.setStorageLocation(storageLocation);
        return this;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getRackNo() {
        return this.rackNo;
    }

    public MedicineBatch rackNo(String rackNo) {
        this.setRackNo(rackNo);
        return this;
    }

    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public MedicineBatch isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public MedicineBatch createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public MedicineBatch createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public MedicineBatch updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public MedicineBatch updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Medicine getMed() {
        return this.med;
    }

    public void setMed(Medicine medicine) {
        this.med = medicine;
    }

    public MedicineBatch med(Medicine medicine) {
        this.setMed(medicine);
        return this;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public MedicineBatch hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    public Admission getAdmissions() {
        return this.admissions;
    }

    public void setAdmissions(Admission admission) {
        this.admissions = admission;
    }

    public MedicineBatch admissions(Admission admission) {
        this.setAdmissions(admission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicineBatch)) {
            return false;
        }
        return getId() != null && getId().equals(((MedicineBatch) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicineBatch{" +
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
            "}";
    }
}
