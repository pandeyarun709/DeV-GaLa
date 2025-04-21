package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.VisitStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DoctorVisit.
 */
@Entity
@Table(name = "doctor_visit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VisitStatus status;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "visit_time")
    private Instant visitTime;

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

    @JsonIgnoreProperties(value = { "medicineDoses", "diagnosticTests", "doctorVisitType" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "slots", "doctorVisits", "doctor" }, allowSetters = true)
    private DoctorVisitType doctorVisitType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "address", "diagnosticTestReports", "doctorVisits", "admissions" }, allowSetters = true)
    private Patient patient;

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

    public DoctorVisit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public DoctorVisit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VisitStatus getStatus() {
        return this.status;
    }

    public DoctorVisit status(VisitStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public LocalDate getVisitDate() {
        return this.visitDate;
    }

    public DoctorVisit visitDate(LocalDate visitDate) {
        this.setVisitDate(visitDate);
        return this;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public Instant getVisitTime() {
        return this.visitTime;
    }

    public DoctorVisit visitTime(Instant visitTime) {
        this.setVisitTime(visitTime);
        return this;
    }

    public void setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DoctorVisit isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DoctorVisit createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public DoctorVisit createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DoctorVisit updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public DoctorVisit updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Prescription getPrescription() {
        return this.prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public DoctorVisit prescription(Prescription prescription) {
        this.setPrescription(prescription);
        return this;
    }

    public DoctorVisitType getDoctorVisitType() {
        return this.doctorVisitType;
    }

    public void setDoctorVisitType(DoctorVisitType doctorVisitType) {
        this.doctorVisitType = doctorVisitType;
    }

    public DoctorVisit doctorVisitType(DoctorVisitType doctorVisitType) {
        this.setDoctorVisitType(doctorVisitType);
        return this;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DoctorVisit patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Admission getAdmissions() {
        return this.admissions;
    }

    public void setAdmissions(Admission admission) {
        this.admissions = admission;
    }

    public DoctorVisit admissions(Admission admission) {
        this.setAdmissions(admission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorVisit)) {
            return false;
        }
        return getId() != null && getId().equals(((DoctorVisit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorVisit{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", visitDate='" + getVisitDate() + "'" +
            ", visitTime='" + getVisitTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
