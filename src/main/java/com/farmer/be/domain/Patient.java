package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.BloodGroup;
import com.farmer.be.domain.enumeration.Relationship;
import com.farmer.be.domain.enumeration.Sex;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "registration_id")
    private String registrationId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    private Long emergencyContactPhone;

    @Column(name = "emergency_contact_email")
    private String emergencyContactEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "emergency_contact_relation_ship")
    private Relationship emergencyContactRelationShip;

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

    @JsonIgnoreProperties(value = { "hospital", "patient" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "test", "admissions" }, allowSetters = true)
    private Set<DiagnosticTestReport> diagnosticTestReports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prescription", "doctorVisitType", "patient", "admissions" }, allowSetters = true)
    private Set<DoctorVisit> doctorVisits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
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

    public Patient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return this.registrationId;
    }

    public Patient registrationId(String registrationId) {
        this.setRegistrationId(registrationId);
        return this;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getName() {
        return this.name;
    }

    public Patient name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Patient phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Patient email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return this.sex;
    }

    public Patient sex(Sex sex) {
        this.setSex(sex);
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Patient dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BloodGroup getBloodGroup() {
        return this.bloodGroup;
    }

    public Patient bloodGroup(BloodGroup bloodGroup) {
        this.setBloodGroup(bloodGroup);
        return this;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContactName() {
        return this.emergencyContactName;
    }

    public Patient emergencyContactName(String emergencyContactName) {
        this.setEmergencyContactName(emergencyContactName);
        return this;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public Long getEmergencyContactPhone() {
        return this.emergencyContactPhone;
    }

    public Patient emergencyContactPhone(Long emergencyContactPhone) {
        this.setEmergencyContactPhone(emergencyContactPhone);
        return this;
    }

    public void setEmergencyContactPhone(Long emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactEmail() {
        return this.emergencyContactEmail;
    }

    public Patient emergencyContactEmail(String emergencyContactEmail) {
        this.setEmergencyContactEmail(emergencyContactEmail);
        return this;
    }

    public void setEmergencyContactEmail(String emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    public Relationship getEmergencyContactRelationShip() {
        return this.emergencyContactRelationShip;
    }

    public Patient emergencyContactRelationShip(Relationship emergencyContactRelationShip) {
        this.setEmergencyContactRelationShip(emergencyContactRelationShip);
        return this;
    }

    public void setEmergencyContactRelationShip(Relationship emergencyContactRelationShip) {
        this.emergencyContactRelationShip = emergencyContactRelationShip;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Patient isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Patient createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Patient createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Patient updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Patient updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Patient address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<DiagnosticTestReport> getDiagnosticTestReports() {
        return this.diagnosticTestReports;
    }

    public void setDiagnosticTestReports(Set<DiagnosticTestReport> diagnosticTestReports) {
        if (this.diagnosticTestReports != null) {
            this.diagnosticTestReports.forEach(i -> i.setPatient(null));
        }
        if (diagnosticTestReports != null) {
            diagnosticTestReports.forEach(i -> i.setPatient(this));
        }
        this.diagnosticTestReports = diagnosticTestReports;
    }

    public Patient diagnosticTestReports(Set<DiagnosticTestReport> diagnosticTestReports) {
        this.setDiagnosticTestReports(diagnosticTestReports);
        return this;
    }

    public Patient addDiagnosticTestReport(DiagnosticTestReport diagnosticTestReport) {
        this.diagnosticTestReports.add(diagnosticTestReport);
        diagnosticTestReport.setPatient(this);
        return this;
    }

    public Patient removeDiagnosticTestReport(DiagnosticTestReport diagnosticTestReport) {
        this.diagnosticTestReports.remove(diagnosticTestReport);
        diagnosticTestReport.setPatient(null);
        return this;
    }

    public Set<DoctorVisit> getDoctorVisits() {
        return this.doctorVisits;
    }

    public void setDoctorVisits(Set<DoctorVisit> doctorVisits) {
        if (this.doctorVisits != null) {
            this.doctorVisits.forEach(i -> i.setPatient(null));
        }
        if (doctorVisits != null) {
            doctorVisits.forEach(i -> i.setPatient(this));
        }
        this.doctorVisits = doctorVisits;
    }

    public Patient doctorVisits(Set<DoctorVisit> doctorVisits) {
        this.setDoctorVisits(doctorVisits);
        return this;
    }

    public Patient addDoctorVisit(DoctorVisit doctorVisit) {
        this.doctorVisits.add(doctorVisit);
        doctorVisit.setPatient(this);
        return this;
    }

    public Patient removeDoctorVisit(DoctorVisit doctorVisit) {
        this.doctorVisits.remove(doctorVisit);
        doctorVisit.setPatient(null);
        return this;
    }

    public Set<Admission> getAdmissions() {
        return this.admissions;
    }

    public void setAdmissions(Set<Admission> admissions) {
        if (this.admissions != null) {
            this.admissions.forEach(i -> i.setPatient(null));
        }
        if (admissions != null) {
            admissions.forEach(i -> i.setPatient(this));
        }
        this.admissions = admissions;
    }

    public Patient admissions(Set<Admission> admissions) {
        this.setAdmissions(admissions);
        return this;
    }

    public Patient addAdmission(Admission admission) {
        this.admissions.add(admission);
        admission.setPatient(this);
        return this;
    }

    public Patient removeAdmission(Admission admission) {
        this.admissions.remove(admission);
        admission.setPatient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return getId() != null && getId().equals(((Patient) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
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
            "}";
    }
}
