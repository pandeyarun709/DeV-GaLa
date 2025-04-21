package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.AdmissionStatus;
import com.farmer.be.domain.enumeration.DischargeStatus;
import com.farmer.be.domain.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Admission.
 */
@Entity
@Table(name = "admission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Admission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "details")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(name = "admission_status")
    private AdmissionStatus admissionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "discharge_status")
    private DischargeStatus dischargeStatus;

    @Column(name = "admission_time")
    private Instant admissionTime;

    @Column(name = "discharge_time")
    private Instant dischargeTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "total_bill_amount")
    private Double totalBillAmount;

    @Column(name = "insurance_covered_amount")
    private Double insuranceCoveredAmount;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "med", "hospital", "admissions" }, allowSetters = true)
    private Set<MedicineBatch> medicineBatches = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "test", "admissions" }, allowSetters = true)
    private Set<DiagnosticTestReport> diagnosticTestReports = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prescription", "doctorVisitType", "patient", "admissions" }, allowSetters = true)
    private Set<DoctorVisit> doctorVisits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admission")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "admission" }, allowSetters = true)
    private Set<LedgerItem> ledgerItems = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_admission__beds",
        joinColumns = @JoinColumn(name = "admission_id"),
        inverseJoinColumns = @JoinColumn(name = "beds_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hospital", "admissions" }, allowSetters = true)
    private Set<Bed> beds = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "address", "diagnosticTestReports", "doctorVisits", "admissions" }, allowSetters = true)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "address", "patientRegistrationDetails", "departments", "beds", "medicineBatches", "admissions", "client" },
        allowSetters = true
    )
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "doctorVisitTypes", "admissions", "qualifications", "department", "test" }, allowSetters = true)
    private Employee admittedUnder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Admission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return this.details;
    }

    public Admission details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public AdmissionStatus getAdmissionStatus() {
        return this.admissionStatus;
    }

    public Admission admissionStatus(AdmissionStatus admissionStatus) {
        this.setAdmissionStatus(admissionStatus);
        return this;
    }

    public void setAdmissionStatus(AdmissionStatus admissionStatus) {
        this.admissionStatus = admissionStatus;
    }

    public DischargeStatus getDischargeStatus() {
        return this.dischargeStatus;
    }

    public Admission dischargeStatus(DischargeStatus dischargeStatus) {
        this.setDischargeStatus(dischargeStatus);
        return this;
    }

    public void setDischargeStatus(DischargeStatus dischargeStatus) {
        this.dischargeStatus = dischargeStatus;
    }

    public Instant getAdmissionTime() {
        return this.admissionTime;
    }

    public Admission admissionTime(Instant admissionTime) {
        this.setAdmissionTime(admissionTime);
        return this;
    }

    public void setAdmissionTime(Instant admissionTime) {
        this.admissionTime = admissionTime;
    }

    public Instant getDischargeTime() {
        return this.dischargeTime;
    }

    public Admission dischargeTime(Instant dischargeTime) {
        this.setDischargeTime(dischargeTime);
        return this;
    }

    public void setDischargeTime(Instant dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public Admission paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getTotalBillAmount() {
        return this.totalBillAmount;
    }

    public Admission totalBillAmount(Double totalBillAmount) {
        this.setTotalBillAmount(totalBillAmount);
        return this;
    }

    public void setTotalBillAmount(Double totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    public Double getInsuranceCoveredAmount() {
        return this.insuranceCoveredAmount;
    }

    public Admission insuranceCoveredAmount(Double insuranceCoveredAmount) {
        this.setInsuranceCoveredAmount(insuranceCoveredAmount);
        return this;
    }

    public void setInsuranceCoveredAmount(Double insuranceCoveredAmount) {
        this.insuranceCoveredAmount = insuranceCoveredAmount;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Admission isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Admission createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Admission createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Admission updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Admission updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<MedicineBatch> getMedicineBatches() {
        return this.medicineBatches;
    }

    public void setMedicineBatches(Set<MedicineBatch> medicineBatches) {
        if (this.medicineBatches != null) {
            this.medicineBatches.forEach(i -> i.setAdmissions(null));
        }
        if (medicineBatches != null) {
            medicineBatches.forEach(i -> i.setAdmissions(this));
        }
        this.medicineBatches = medicineBatches;
    }

    public Admission medicineBatches(Set<MedicineBatch> medicineBatches) {
        this.setMedicineBatches(medicineBatches);
        return this;
    }

    public Admission addMedicineBatch(MedicineBatch medicineBatch) {
        this.medicineBatches.add(medicineBatch);
        medicineBatch.setAdmissions(this);
        return this;
    }

    public Admission removeMedicineBatch(MedicineBatch medicineBatch) {
        this.medicineBatches.remove(medicineBatch);
        medicineBatch.setAdmissions(null);
        return this;
    }

    public Set<DiagnosticTestReport> getDiagnosticTestReports() {
        return this.diagnosticTestReports;
    }

    public void setDiagnosticTestReports(Set<DiagnosticTestReport> diagnosticTestReports) {
        if (this.diagnosticTestReports != null) {
            this.diagnosticTestReports.forEach(i -> i.setAdmissions(null));
        }
        if (diagnosticTestReports != null) {
            diagnosticTestReports.forEach(i -> i.setAdmissions(this));
        }
        this.diagnosticTestReports = diagnosticTestReports;
    }

    public Admission diagnosticTestReports(Set<DiagnosticTestReport> diagnosticTestReports) {
        this.setDiagnosticTestReports(diagnosticTestReports);
        return this;
    }

    public Admission addDiagnosticTestReport(DiagnosticTestReport diagnosticTestReport) {
        this.diagnosticTestReports.add(diagnosticTestReport);
        diagnosticTestReport.setAdmissions(this);
        return this;
    }

    public Admission removeDiagnosticTestReport(DiagnosticTestReport diagnosticTestReport) {
        this.diagnosticTestReports.remove(diagnosticTestReport);
        diagnosticTestReport.setAdmissions(null);
        return this;
    }

    public Set<DoctorVisit> getDoctorVisits() {
        return this.doctorVisits;
    }

    public void setDoctorVisits(Set<DoctorVisit> doctorVisits) {
        if (this.doctorVisits != null) {
            this.doctorVisits.forEach(i -> i.setAdmissions(null));
        }
        if (doctorVisits != null) {
            doctorVisits.forEach(i -> i.setAdmissions(this));
        }
        this.doctorVisits = doctorVisits;
    }

    public Admission doctorVisits(Set<DoctorVisit> doctorVisits) {
        this.setDoctorVisits(doctorVisits);
        return this;
    }

    public Admission addDoctorVisit(DoctorVisit doctorVisit) {
        this.doctorVisits.add(doctorVisit);
        doctorVisit.setAdmissions(this);
        return this;
    }

    public Admission removeDoctorVisit(DoctorVisit doctorVisit) {
        this.doctorVisits.remove(doctorVisit);
        doctorVisit.setAdmissions(null);
        return this;
    }

    public Set<LedgerItem> getLedgerItems() {
        return this.ledgerItems;
    }

    public void setLedgerItems(Set<LedgerItem> ledgerItems) {
        if (this.ledgerItems != null) {
            this.ledgerItems.forEach(i -> i.setAdmission(null));
        }
        if (ledgerItems != null) {
            ledgerItems.forEach(i -> i.setAdmission(this));
        }
        this.ledgerItems = ledgerItems;
    }

    public Admission ledgerItems(Set<LedgerItem> ledgerItems) {
        this.setLedgerItems(ledgerItems);
        return this;
    }

    public Admission addLedgerItem(LedgerItem ledgerItem) {
        this.ledgerItems.add(ledgerItem);
        ledgerItem.setAdmission(this);
        return this;
    }

    public Admission removeLedgerItem(LedgerItem ledgerItem) {
        this.ledgerItems.remove(ledgerItem);
        ledgerItem.setAdmission(null);
        return this;
    }

    public Set<Bed> getBeds() {
        return this.beds;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }

    public Admission beds(Set<Bed> beds) {
        this.setBeds(beds);
        return this;
    }

    public Admission addBeds(Bed bed) {
        this.beds.add(bed);
        return this;
    }

    public Admission removeBeds(Bed bed) {
        this.beds.remove(bed);
        return this;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Admission patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Admission hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    public Employee getAdmittedUnder() {
        return this.admittedUnder;
    }

    public void setAdmittedUnder(Employee employee) {
        this.admittedUnder = employee;
    }

    public Admission admittedUnder(Employee employee) {
        this.setAdmittedUnder(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admission)) {
            return false;
        }
        return getId() != null && getId().equals(((Admission) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Admission{" +
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
            "}";
    }
}
