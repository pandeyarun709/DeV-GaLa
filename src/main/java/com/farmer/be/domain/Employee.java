package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.EmployeeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

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

    @Column(name = "rmo_hourly_rate")
    private Double rmoHourlyRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EmployeeType type;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "slots", "doctorVisits", "doctor" }, allowSetters = true)
    private Set<DoctorVisitType> doctorVisitTypes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admittedUnder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "medicineBatches", "diagnosticTestReports", "doctorVisits", "ledgerItems", "beds", "patient", "hospital", "admittedUnder",
        },
        allowSetters = true
    )
    private Set<Admission> admissions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_employee__qualifications",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "qualifications_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Set<Qualification> qualifications = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "diagnosticTests", "hospital" }, allowSetters = true)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "diagnosticTestReports", "slots", "department", "prescription" }, allowSetters = true)
    private DiagnosticTest test;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Employee name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Employee phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public Employee registrationNo(String registrationNo) {
        this.setRegistrationNo(registrationNo);
        return this;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Double getRmoHourlyRate() {
        return this.rmoHourlyRate;
    }

    public Employee rmoHourlyRate(Double rmoHourlyRate) {
        this.setRmoHourlyRate(rmoHourlyRate);
        return this;
    }

    public void setRmoHourlyRate(Double rmoHourlyRate) {
        this.rmoHourlyRate = rmoHourlyRate;
    }

    public EmployeeType getType() {
        return this.type;
    }

    public Employee type(EmployeeType type) {
        this.setType(type);
        return this;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Employee isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Employee createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Employee createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Employee updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Employee updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<DoctorVisitType> getDoctorVisitTypes() {
        return this.doctorVisitTypes;
    }

    public void setDoctorVisitTypes(Set<DoctorVisitType> doctorVisitTypes) {
        if (this.doctorVisitTypes != null) {
            this.doctorVisitTypes.forEach(i -> i.setDoctor(null));
        }
        if (doctorVisitTypes != null) {
            doctorVisitTypes.forEach(i -> i.setDoctor(this));
        }
        this.doctorVisitTypes = doctorVisitTypes;
    }

    public Employee doctorVisitTypes(Set<DoctorVisitType> doctorVisitTypes) {
        this.setDoctorVisitTypes(doctorVisitTypes);
        return this;
    }

    public Employee addDoctorVisitType(DoctorVisitType doctorVisitType) {
        this.doctorVisitTypes.add(doctorVisitType);
        doctorVisitType.setDoctor(this);
        return this;
    }

    public Employee removeDoctorVisitType(DoctorVisitType doctorVisitType) {
        this.doctorVisitTypes.remove(doctorVisitType);
        doctorVisitType.setDoctor(null);
        return this;
    }

    public Set<Admission> getAdmissions() {
        return this.admissions;
    }

    public void setAdmissions(Set<Admission> admissions) {
        if (this.admissions != null) {
            this.admissions.forEach(i -> i.setAdmittedUnder(null));
        }
        if (admissions != null) {
            admissions.forEach(i -> i.setAdmittedUnder(this));
        }
        this.admissions = admissions;
    }

    public Employee admissions(Set<Admission> admissions) {
        this.setAdmissions(admissions);
        return this;
    }

    public Employee addAdmission(Admission admission) {
        this.admissions.add(admission);
        admission.setAdmittedUnder(this);
        return this;
    }

    public Employee removeAdmission(Admission admission) {
        this.admissions.remove(admission);
        admission.setAdmittedUnder(null);
        return this;
    }

    public Set<Qualification> getQualifications() {
        return this.qualifications;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public Employee qualifications(Set<Qualification> qualifications) {
        this.setQualifications(qualifications);
        return this;
    }

    public Employee addQualifications(Qualification qualification) {
        this.qualifications.add(qualification);
        return this;
    }

    public Employee removeQualifications(Qualification qualification) {
        this.qualifications.remove(qualification);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public DiagnosticTest getTest() {
        return this.test;
    }

    public void setTest(DiagnosticTest diagnosticTest) {
        this.test = diagnosticTest;
    }

    public Employee test(DiagnosticTest diagnosticTest) {
        this.setTest(diagnosticTest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", email='" + getEmail() + "'" +
            ", registrationNo='" + getRegistrationNo() + "'" +
            ", rmoHourlyRate=" + getRmoHourlyRate() +
            ", type='" + getType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
