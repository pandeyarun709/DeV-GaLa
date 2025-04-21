package com.farmer.be.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.DiagnosticTestReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiagnosticTestReportDTO implements Serializable {

    private Long id;

    private String description;

    private String signedBy;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private PatientDTO patient;

    private DiagnosticTestDTO test;

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

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
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

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public DiagnosticTestDTO getTest() {
        return test;
    }

    public void setTest(DiagnosticTestDTO test) {
        this.test = test;
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
        if (!(o instanceof DiagnosticTestReportDTO)) {
            return false;
        }

        DiagnosticTestReportDTO diagnosticTestReportDTO = (DiagnosticTestReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, diagnosticTestReportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiagnosticTestReportDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", signedBy='" + getSignedBy() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", patient=" + getPatient() +
            ", test=" + getTest() +
            ", admissions=" + getAdmissions() +
            "}";
    }
}
