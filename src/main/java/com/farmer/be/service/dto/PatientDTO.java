package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.BloodGroup;
import com.farmer.be.domain.enumeration.Relationship;
import com.farmer.be.domain.enumeration.Sex;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.Patient} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientDTO implements Serializable {

    private Long id;

    private String registrationId;

    private String name;

    private Long phone;

    private String email;

    private Sex sex;

    private LocalDate dateOfBirth;

    private BloodGroup bloodGroup;

    private String emergencyContactName;

    private Long emergencyContactPhone;

    private String emergencyContactEmail;

    private Relationship emergencyContactRelationShip;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public Long getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(Long emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactEmail() {
        return emergencyContactEmail;
    }

    public void setEmergencyContactEmail(String emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    public Relationship getEmergencyContactRelationShip() {
        return emergencyContactRelationShip;
    }

    public void setEmergencyContactRelationShip(Relationship emergencyContactRelationShip) {
        this.emergencyContactRelationShip = emergencyContactRelationShip;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientDTO)) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", registrationId='" + getRegistrationId() + "'" +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", email='" + getEmail() + "'" +
            ", sex='" + getSex() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", emergencyContactName='" + getEmergencyContactName() + "'" +
            ", emergencyContactPhone=" + getEmergencyContactPhone() +
            ", emergencyContactEmail='" + getEmergencyContactEmail() + "'" +
            ", emergencyContactRelationShip='" + getEmergencyContactRelationShip() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", address=" + getAddress() +
            "}";
    }
}
