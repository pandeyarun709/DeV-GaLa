package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.VisitType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DoctorVisitType.
 */
@Entity
@Table(name = "doctor_visit_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorVisitType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private VisitType type;

    @Column(name = "fee")
    private Double fee;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctorVisitType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "doctorVisitType", "test" }, allowSetters = true)
    private Set<Slot> slots = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctorVisitType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prescription", "doctorVisitType", "patient", "admissions" }, allowSetters = true)
    private Set<DoctorVisit> doctorVisits = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "doctorVisitTypes", "admissions", "qualifications", "department", "test" }, allowSetters = true)
    private Employee doctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DoctorVisitType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisitType getType() {
        return this.type;
    }

    public DoctorVisitType type(VisitType type) {
        this.setType(type);
        return this;
    }

    public void setType(VisitType type) {
        this.type = type;
    }

    public Double getFee() {
        return this.fee;
    }

    public DoctorVisitType fee(Double fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getIsInsuranceCovered() {
        return this.isInsuranceCovered;
    }

    public DoctorVisitType isInsuranceCovered(Boolean isInsuranceCovered) {
        this.setIsInsuranceCovered(isInsuranceCovered);
        return this;
    }

    public void setIsInsuranceCovered(Boolean isInsuranceCovered) {
        this.isInsuranceCovered = isInsuranceCovered;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DoctorVisitType isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DoctorVisitType createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public DoctorVisitType createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DoctorVisitType updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public DoctorVisitType updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Slot> getSlots() {
        return this.slots;
    }

    public void setSlots(Set<Slot> slots) {
        if (this.slots != null) {
            this.slots.forEach(i -> i.setDoctorVisitType(null));
        }
        if (slots != null) {
            slots.forEach(i -> i.setDoctorVisitType(this));
        }
        this.slots = slots;
    }

    public DoctorVisitType slots(Set<Slot> slots) {
        this.setSlots(slots);
        return this;
    }

    public DoctorVisitType addSlot(Slot slot) {
        this.slots.add(slot);
        slot.setDoctorVisitType(this);
        return this;
    }

    public DoctorVisitType removeSlot(Slot slot) {
        this.slots.remove(slot);
        slot.setDoctorVisitType(null);
        return this;
    }

    public Set<DoctorVisit> getDoctorVisits() {
        return this.doctorVisits;
    }

    public void setDoctorVisits(Set<DoctorVisit> doctorVisits) {
        if (this.doctorVisits != null) {
            this.doctorVisits.forEach(i -> i.setDoctorVisitType(null));
        }
        if (doctorVisits != null) {
            doctorVisits.forEach(i -> i.setDoctorVisitType(this));
        }
        this.doctorVisits = doctorVisits;
    }

    public DoctorVisitType doctorVisits(Set<DoctorVisit> doctorVisits) {
        this.setDoctorVisits(doctorVisits);
        return this;
    }

    public DoctorVisitType addDoctorVisit(DoctorVisit doctorVisit) {
        this.doctorVisits.add(doctorVisit);
        doctorVisit.setDoctorVisitType(this);
        return this;
    }

    public DoctorVisitType removeDoctorVisit(DoctorVisit doctorVisit) {
        this.doctorVisits.remove(doctorVisit);
        doctorVisit.setDoctorVisitType(null);
        return this;
    }

    public Employee getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Employee employee) {
        this.doctor = employee;
    }

    public DoctorVisitType doctor(Employee employee) {
        this.setDoctor(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorVisitType)) {
            return false;
        }
        return getId() != null && getId().equals(((DoctorVisitType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorVisitType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", fee=" + getFee() +
            ", isInsuranceCovered='" + getIsInsuranceCovered() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
