package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.AdmissionStatus;
import com.farmer.be.domain.enumeration.DischargeStatus;
import com.farmer.be.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.farmer.be.domain.Admission} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdmissionDTO implements Serializable {

    private Long id;

    private String details;

    private AdmissionStatus admissionStatus;

    private DischargeStatus dischargeStatus;

    private Instant admissionTime;

    private Instant dischargeTime;

    private PaymentStatus paymentStatus;

    private Double totalBillAmount;

    private Double insuranceCoveredAmount;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private Set<BedDTO> beds = new HashSet<>();

    private PatientDTO patient;

    private HospitalDTO hospital;

    private EmployeeDTO admittedUnder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public AdmissionStatus getAdmissionStatus() {
        return admissionStatus;
    }

    public void setAdmissionStatus(AdmissionStatus admissionStatus) {
        this.admissionStatus = admissionStatus;
    }

    public DischargeStatus getDischargeStatus() {
        return dischargeStatus;
    }

    public void setDischargeStatus(DischargeStatus dischargeStatus) {
        this.dischargeStatus = dischargeStatus;
    }

    public Instant getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Instant admissionTime) {
        this.admissionTime = admissionTime;
    }

    public Instant getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(Instant dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(Double totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    public Double getInsuranceCoveredAmount() {
        return insuranceCoveredAmount;
    }

    public void setInsuranceCoveredAmount(Double insuranceCoveredAmount) {
        this.insuranceCoveredAmount = insuranceCoveredAmount;
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

    public Set<BedDTO> getBeds() {
        return beds;
    }

    public void setBeds(Set<BedDTO> beds) {
        this.beds = beds;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public EmployeeDTO getAdmittedUnder() {
        return admittedUnder;
    }

    public void setAdmittedUnder(EmployeeDTO admittedUnder) {
        this.admittedUnder = admittedUnder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdmissionDTO)) {
            return false;
        }

        AdmissionDTO admissionDTO = (AdmissionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, admissionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdmissionDTO{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", admissionStatus='" + getAdmissionStatus() + "'" +
            ", dischargeStatus='" + getDischargeStatus() + "'" +
            ", admissionTime='" + getAdmissionTime() + "'" +
            ", dischargeTime='" + getDischargeTime() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", totalBillAmount=" + getTotalBillAmount() +
            ", insuranceCoveredAmount=" + getInsuranceCoveredAmount() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", beds=" + getBeds() +
            ", patient=" + getPatient() +
            ", hospital=" + getHospital() +
            ", admittedUnder=" + getAdmittedUnder() +
            "}";
    }
}
