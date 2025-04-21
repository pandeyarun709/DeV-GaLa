package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PatientRegistrationDetails.
 */
@Entity
@Table(name = "patient_registration_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientRegistrationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "registration_id")
    private String registrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hospitals", "patientRegistrationDetails" }, allowSetters = true)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "address", "patientRegistrationDetails", "departments", "beds", "medicineBatches", "admissions", "client" },
        allowSetters = true
    )
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "patientRegistrationDetails" }, allowSetters = true)
    private ReferralDoctor referredBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PatientRegistrationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return this.registrationId;
    }

    public PatientRegistrationDetails registrationId(String registrationId) {
        this.setRegistrationId(registrationId);
        return this;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PatientRegistrationDetails client(Client client) {
        this.setClient(client);
        return this;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public PatientRegistrationDetails hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    public ReferralDoctor getReferredBy() {
        return this.referredBy;
    }

    public void setReferredBy(ReferralDoctor referralDoctor) {
        this.referredBy = referralDoctor;
    }

    public PatientRegistrationDetails referredBy(ReferralDoctor referralDoctor) {
        this.setReferredBy(referralDoctor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientRegistrationDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((PatientRegistrationDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientRegistrationDetails{" +
            "id=" + getId() +
            ", registrationId='" + getRegistrationId() + "'" +
            "}";
    }
}
