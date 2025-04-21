package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LedgerItem.
 */
@Entity
@Table(name = "ledger_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LedgerItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time")
    private Instant time;

    @Column(name = "description")
    private String description;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "is_covered_by_insurance")
    private Boolean isCoveredByInsurance;

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
    @JsonIgnoreProperties(
        value = {
            "medicineBatches", "diagnosticTestReports", "doctorVisits", "ledgerItems", "beds", "patient", "hospital", "admittedUnder",
        },
        allowSetters = true
    )
    private Admission admission;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LedgerItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return this.time;
    }

    public LedgerItem time(Instant time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getDescription() {
        return this.description;
    }

    public LedgerItem description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return this.fee;
    }

    public LedgerItem fee(Double fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getIsCoveredByInsurance() {
        return this.isCoveredByInsurance;
    }

    public LedgerItem isCoveredByInsurance(Boolean isCoveredByInsurance) {
        this.setIsCoveredByInsurance(isCoveredByInsurance);
        return this;
    }

    public void setIsCoveredByInsurance(Boolean isCoveredByInsurance) {
        this.isCoveredByInsurance = isCoveredByInsurance;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public LedgerItem isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LedgerItem createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public LedgerItem createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public LedgerItem updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public LedgerItem updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Admission getAdmission() {
        return this.admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public LedgerItem admission(Admission admission) {
        this.setAdmission(admission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LedgerItem)) {
            return false;
        }
        return getId() != null && getId().equals(((LedgerItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LedgerItem{" +
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
            "}";
    }
}
