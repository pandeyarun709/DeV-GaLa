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
 * A DiagnosticTest.
 */
@Entity
@Table(name = "diagnostic_test")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiagnosticTest implements Serializable {

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

    @Column(name = "fee")
    private Double fee;

    @Column(name = "is_insurance_covered")
    private Boolean isInsuranceCovered;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "doctorVisitTypes", "admissions", "qualifications", "department", "test" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "test", "admissions" }, allowSetters = true)
    private Set<DiagnosticTestReport> diagnosticTestReports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "doctorVisitType", "test" }, allowSetters = true)
    private Set<Slot> slots = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "diagnosticTests", "hospital" }, allowSetters = true)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "medicineDoses", "diagnosticTests", "doctorVisitType" }, allowSetters = true)
    private Prescription prescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DiagnosticTest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DiagnosticTest name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return this.phone;
    }

    public DiagnosticTest phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public DiagnosticTest email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getFee() {
        return this.fee;
    }

    public DiagnosticTest fee(Double fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getIsInsuranceCovered() {
        return this.isInsuranceCovered;
    }

    public DiagnosticTest isInsuranceCovered(Boolean isInsuranceCovered) {
        this.setIsInsuranceCovered(isInsuranceCovered);
        return this;
    }

    public void setIsInsuranceCovered(Boolean isInsuranceCovered) {
        this.isInsuranceCovered = isInsuranceCovered;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DiagnosticTest isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DiagnosticTest createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public DiagnosticTest createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DiagnosticTest updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public DiagnosticTest updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setTest(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setTest(this));
        }
        this.employees = employees;
    }

    public DiagnosticTest employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public DiagnosticTest addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setTest(this);
        return this;
    }

    public DiagnosticTest removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setTest(null);
        return this;
    }

    public Set<DiagnosticTestReport> getDiagnosticTestReports() {
        return this.diagnosticTestReports;
    }

    public void setDiagnosticTestReports(Set<DiagnosticTestReport> diagnosticTestReports) {
        if (this.diagnosticTestReports != null) {
            this.diagnosticTestReports.forEach(i -> i.setTest(null));
        }
        if (diagnosticTestReports != null) {
            diagnosticTestReports.forEach(i -> i.setTest(this));
        }
        this.diagnosticTestReports = diagnosticTestReports;
    }

    public DiagnosticTest diagnosticTestReports(Set<DiagnosticTestReport> diagnosticTestReports) {
        this.setDiagnosticTestReports(diagnosticTestReports);
        return this;
    }

    public DiagnosticTest addDiagnosticTestReport(DiagnosticTestReport diagnosticTestReport) {
        this.diagnosticTestReports.add(diagnosticTestReport);
        diagnosticTestReport.setTest(this);
        return this;
    }

    public DiagnosticTest removeDiagnosticTestReport(DiagnosticTestReport diagnosticTestReport) {
        this.diagnosticTestReports.remove(diagnosticTestReport);
        diagnosticTestReport.setTest(null);
        return this;
    }

    public Set<Slot> getSlots() {
        return this.slots;
    }

    public void setSlots(Set<Slot> slots) {
        if (this.slots != null) {
            this.slots.forEach(i -> i.setTest(null));
        }
        if (slots != null) {
            slots.forEach(i -> i.setTest(this));
        }
        this.slots = slots;
    }

    public DiagnosticTest slots(Set<Slot> slots) {
        this.setSlots(slots);
        return this;
    }

    public DiagnosticTest addSlot(Slot slot) {
        this.slots.add(slot);
        slot.setTest(this);
        return this;
    }

    public DiagnosticTest removeSlot(Slot slot) {
        this.slots.remove(slot);
        slot.setTest(null);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public DiagnosticTest department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public Prescription getPrescription() {
        return this.prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public DiagnosticTest prescription(Prescription prescription) {
        this.setPrescription(prescription);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiagnosticTest)) {
            return false;
        }
        return getId() != null && getId().equals(((DiagnosticTest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiagnosticTest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", email='" + getEmail() + "'" +
            ", fee=" + getFee() +
            ", isInsuranceCovered='" + getIsInsuranceCovered() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
