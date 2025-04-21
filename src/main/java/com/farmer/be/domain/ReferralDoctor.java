package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReferralDoctor.
 */
@Entity
@Table(name = "referral_doctor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReferralDoctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_no")
    private String registrationNo;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "referredBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "hospital", "referredBy" }, allowSetters = true)
    private Set<PatientRegistrationDetails> patientRegistrationDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReferralDoctor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ReferralDoctor name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return this.phone;
    }

    public ReferralDoctor phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public ReferralDoctor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public ReferralDoctor registrationNo(String registrationNo) {
        this.setRegistrationNo(registrationNo);
        return this;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ReferralDoctor isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ReferralDoctor createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public ReferralDoctor createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ReferralDoctor updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public ReferralDoctor updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<PatientRegistrationDetails> getPatientRegistrationDetails() {
        return this.patientRegistrationDetails;
    }

    public void setPatientRegistrationDetails(Set<PatientRegistrationDetails> patientRegistrationDetails) {
        if (this.patientRegistrationDetails != null) {
            this.patientRegistrationDetails.forEach(i -> i.setReferredBy(null));
        }
        if (patientRegistrationDetails != null) {
            patientRegistrationDetails.forEach(i -> i.setReferredBy(this));
        }
        this.patientRegistrationDetails = patientRegistrationDetails;
    }

    public ReferralDoctor patientRegistrationDetails(Set<PatientRegistrationDetails> patientRegistrationDetails) {
        this.setPatientRegistrationDetails(patientRegistrationDetails);
        return this;
    }

    public ReferralDoctor addPatientRegistrationDetails(PatientRegistrationDetails patientRegistrationDetails) {
        this.patientRegistrationDetails.add(patientRegistrationDetails);
        patientRegistrationDetails.setReferredBy(this);
        return this;
    }

    public ReferralDoctor removePatientRegistrationDetails(PatientRegistrationDetails patientRegistrationDetails) {
        this.patientRegistrationDetails.remove(patientRegistrationDetails);
        patientRegistrationDetails.setReferredBy(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferralDoctor)) {
            return false;
        }
        return getId() != null && getId().equals(((ReferralDoctor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferralDoctor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", email='" + getEmail() + "'" +
            ", registrationNo='" + getRegistrationNo() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
