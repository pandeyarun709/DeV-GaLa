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
 * A Hospital.
 */
@Entity
@Table(name = "hospital")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo_path")
    private String logoPath;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "hospital", "referredBy" }, allowSetters = true)
    private Set<PatientRegistrationDetails> patientRegistrationDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employees", "diagnosticTests", "hospital" }, allowSetters = true)
    private Set<Department> departments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hospital", "admissions" }, allowSetters = true)
    private Set<Bed> beds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "med", "hospital", "admissions" }, allowSetters = true)
    private Set<MedicineBatch> medicineBatches = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hospital")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "medicineBatches", "diagnosticTestReports", "doctorVisits", "ledgerItems", "beds", "patient", "hospital", "admittedUnder",
        },
        allowSetters = true
    )
    private Set<Admission> admissions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hospitals", "patientRegistrationDetails" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hospital id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Hospital name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return this.logoPath;
    }

    public Hospital logoPath(String logoPath) {
        this.setLogoPath(logoPath);
        return this;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Hospital isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Hospital createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Hospital createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Hospital updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Hospital updatedOn(Instant updatedOn) {
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

    public Hospital address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<PatientRegistrationDetails> getPatientRegistrationDetails() {
        return this.patientRegistrationDetails;
    }

    public void setPatientRegistrationDetails(Set<PatientRegistrationDetails> patientRegistrationDetails) {
        if (this.patientRegistrationDetails != null) {
            this.patientRegistrationDetails.forEach(i -> i.setHospital(null));
        }
        if (patientRegistrationDetails != null) {
            patientRegistrationDetails.forEach(i -> i.setHospital(this));
        }
        this.patientRegistrationDetails = patientRegistrationDetails;
    }

    public Hospital patientRegistrationDetails(Set<PatientRegistrationDetails> patientRegistrationDetails) {
        this.setPatientRegistrationDetails(patientRegistrationDetails);
        return this;
    }

    public Hospital addPatientRegistrationDetails(PatientRegistrationDetails patientRegistrationDetails) {
        this.patientRegistrationDetails.add(patientRegistrationDetails);
        patientRegistrationDetails.setHospital(this);
        return this;
    }

    public Hospital removePatientRegistrationDetails(PatientRegistrationDetails patientRegistrationDetails) {
        this.patientRegistrationDetails.remove(patientRegistrationDetails);
        patientRegistrationDetails.setHospital(null);
        return this;
    }

    public Set<Department> getDepartments() {
        return this.departments;
    }

    public void setDepartments(Set<Department> departments) {
        if (this.departments != null) {
            this.departments.forEach(i -> i.setHospital(null));
        }
        if (departments != null) {
            departments.forEach(i -> i.setHospital(this));
        }
        this.departments = departments;
    }

    public Hospital departments(Set<Department> departments) {
        this.setDepartments(departments);
        return this;
    }

    public Hospital addDepartment(Department department) {
        this.departments.add(department);
        department.setHospital(this);
        return this;
    }

    public Hospital removeDepartment(Department department) {
        this.departments.remove(department);
        department.setHospital(null);
        return this;
    }

    public Set<Bed> getBeds() {
        return this.beds;
    }

    public void setBeds(Set<Bed> beds) {
        if (this.beds != null) {
            this.beds.forEach(i -> i.setHospital(null));
        }
        if (beds != null) {
            beds.forEach(i -> i.setHospital(this));
        }
        this.beds = beds;
    }

    public Hospital beds(Set<Bed> beds) {
        this.setBeds(beds);
        return this;
    }

    public Hospital addBed(Bed bed) {
        this.beds.add(bed);
        bed.setHospital(this);
        return this;
    }

    public Hospital removeBed(Bed bed) {
        this.beds.remove(bed);
        bed.setHospital(null);
        return this;
    }

    public Set<MedicineBatch> getMedicineBatches() {
        return this.medicineBatches;
    }

    public void setMedicineBatches(Set<MedicineBatch> medicineBatches) {
        if (this.medicineBatches != null) {
            this.medicineBatches.forEach(i -> i.setHospital(null));
        }
        if (medicineBatches != null) {
            medicineBatches.forEach(i -> i.setHospital(this));
        }
        this.medicineBatches = medicineBatches;
    }

    public Hospital medicineBatches(Set<MedicineBatch> medicineBatches) {
        this.setMedicineBatches(medicineBatches);
        return this;
    }

    public Hospital addMedicineBatch(MedicineBatch medicineBatch) {
        this.medicineBatches.add(medicineBatch);
        medicineBatch.setHospital(this);
        return this;
    }

    public Hospital removeMedicineBatch(MedicineBatch medicineBatch) {
        this.medicineBatches.remove(medicineBatch);
        medicineBatch.setHospital(null);
        return this;
    }

    public Set<Admission> getAdmissions() {
        return this.admissions;
    }

    public void setAdmissions(Set<Admission> admissions) {
        if (this.admissions != null) {
            this.admissions.forEach(i -> i.setHospital(null));
        }
        if (admissions != null) {
            admissions.forEach(i -> i.setHospital(this));
        }
        this.admissions = admissions;
    }

    public Hospital admissions(Set<Admission> admissions) {
        this.setAdmissions(admissions);
        return this;
    }

    public Hospital addAdmission(Admission admission) {
        this.admissions.add(admission);
        admission.setHospital(this);
        return this;
    }

    public Hospital removeAdmission(Admission admission) {
        this.admissions.remove(admission);
        admission.setHospital(null);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Hospital client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospital)) {
            return false;
        }
        return getId() != null && getId().equals(((Hospital) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hospital{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logoPath='" + getLogoPath() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
