package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.BedType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bed.
 */
@Entity
@Table(name = "bed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BedType type;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "room_no")
    private Long roomNo;

    @Column(name = "daily_rate")
    private Double dailyRate;

    @Column(name = "is_insurance_covered")
    private Boolean isInsuranceCovered;

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
        value = { "address", "patientRegistrationDetails", "departments", "beds", "medicineBatches", "admissions", "client" },
        allowSetters = true
    )
    private Hospital hospital;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "beds")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "medicineBatches", "diagnosticTestReports", "doctorVisits", "ledgerItems", "beds", "patient", "hospital", "admittedUnder",
        },
        allowSetters = true
    )
    private Set<Admission> admissions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BedType getType() {
        return this.type;
    }

    public Bed type(BedType type) {
        this.setType(type);
        return this;
    }

    public void setType(BedType type) {
        this.type = type;
    }

    public Long getFloor() {
        return this.floor;
    }

    public Bed floor(Long floor) {
        this.setFloor(floor);
        return this;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    public Long getRoomNo() {
        return this.roomNo;
    }

    public Bed roomNo(Long roomNo) {
        this.setRoomNo(roomNo);
        return this;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }

    public Double getDailyRate() {
        return this.dailyRate;
    }

    public Bed dailyRate(Double dailyRate) {
        this.setDailyRate(dailyRate);
        return this;
    }

    public void setDailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public Boolean getIsInsuranceCovered() {
        return this.isInsuranceCovered;
    }

    public Bed isInsuranceCovered(Boolean isInsuranceCovered) {
        this.setIsInsuranceCovered(isInsuranceCovered);
        return this;
    }

    public void setIsInsuranceCovered(Boolean isInsuranceCovered) {
        this.isInsuranceCovered = isInsuranceCovered;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Bed isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Bed createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Bed createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Bed updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Bed updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Bed hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    public Set<Admission> getAdmissions() {
        return this.admissions;
    }

    public void setAdmissions(Set<Admission> admissions) {
        if (this.admissions != null) {
            this.admissions.forEach(i -> i.removeBeds(this));
        }
        if (admissions != null) {
            admissions.forEach(i -> i.addBeds(this));
        }
        this.admissions = admissions;
    }

    public Bed admissions(Set<Admission> admissions) {
        this.setAdmissions(admissions);
        return this;
    }

    public Bed addAdmissions(Admission admission) {
        this.admissions.add(admission);
        admission.getBeds().add(this);
        return this;
    }

    public Bed removeAdmissions(Admission admission) {
        this.admissions.remove(admission);
        admission.getBeds().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bed)) {
            return false;
        }
        return getId() != null && getId().equals(((Bed) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bed{" +
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
            "}";
    }
}
