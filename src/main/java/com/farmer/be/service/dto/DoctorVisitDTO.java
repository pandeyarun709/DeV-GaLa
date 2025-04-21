package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.VisitStatus;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.DoctorVisit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DoctorVisitDTO implements Serializable {

    private Long id;

    private String description;

    private VisitStatus status;

    private LocalDate visitDate;

    private Instant visitTime;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private PrescriptionDTO prescription;

    private DoctorVisitTypeDTO doctorVisitType;

    private PatientDTO patient;

    private AdmissionDTO admissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public Instant getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
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

    public DoctorVisitTypeDTO getDoctorVisitType() {
        return doctorVisitType;
    }

    public void setDoctorVisitType(DoctorVisitTypeDTO doctorVisitType) {
        this.doctorVisitType = doctorVisitType;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
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
        if (!(o instanceof DoctorVisitDTO)) {
            return false;
        }

        DoctorVisitDTO doctorVisitDTO = (DoctorVisitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, doctorVisitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorVisitDTO{" +
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
            ", prescription=" + getPrescription() +
            ", doctorVisitType=" + getDoctorVisitType() +
            ", patient=" + getPatient() +
            ", admissions=" + getAdmissions() +
            "}";
    }
}
