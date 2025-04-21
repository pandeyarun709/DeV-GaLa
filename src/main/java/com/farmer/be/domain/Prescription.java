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
 * A Prescription.
 */
@Entity
@Table(name = "prescription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "history")
    private String history;

    @Column(name = "compliant")
    private String compliant;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "bp_high")
    private Double bpHigh;

    @Column(name = "bp_low")
    private Double bpLow;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "other_vital")
    private String otherVital;

    @Column(name = "description")
    private String description;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prescription")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prescription", "medicine" }, allowSetters = true)
    private Set<MedicineDose> medicineDoses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prescription")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employees", "diagnosticTestReports", "slots", "department", "prescription" }, allowSetters = true)
    private Set<DiagnosticTest> diagnosticTests = new HashSet<>();

    @JsonIgnoreProperties(value = { "prescription", "doctorVisitType", "patient", "admissions" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "prescription")
    private DoctorVisit doctorVisitType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prescription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHistory() {
        return this.history;
    }

    public Prescription history(String history) {
        this.setHistory(history);
        return this;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getCompliant() {
        return this.compliant;
    }

    public Prescription compliant(String compliant) {
        this.setCompliant(compliant);
        return this;
    }

    public void setCompliant(String compliant) {
        this.compliant = compliant;
    }

    public Double getHeight() {
        return this.height;
    }

    public Prescription height(Double height) {
        this.setHeight(height);
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Prescription weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBpHigh() {
        return this.bpHigh;
    }

    public Prescription bpHigh(Double bpHigh) {
        this.setBpHigh(bpHigh);
        return this;
    }

    public void setBpHigh(Double bpHigh) {
        this.bpHigh = bpHigh;
    }

    public Double getBpLow() {
        return this.bpLow;
    }

    public Prescription bpLow(Double bpLow) {
        this.setBpLow(bpLow);
        return this;
    }

    public void setBpLow(Double bpLow) {
        this.bpLow = bpLow;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public Prescription temperature(Double temperature) {
        this.setTemperature(temperature);
        return this;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getOtherVital() {
        return this.otherVital;
    }

    public Prescription otherVital(String otherVital) {
        this.setOtherVital(otherVital);
        return this;
    }

    public void setOtherVital(String otherVital) {
        this.otherVital = otherVital;
    }

    public String getDescription() {
        return this.description;
    }

    public Prescription description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Prescription isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Prescription createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Prescription createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Prescription updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Prescription updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<MedicineDose> getMedicineDoses() {
        return this.medicineDoses;
    }

    public void setMedicineDoses(Set<MedicineDose> medicineDoses) {
        if (this.medicineDoses != null) {
            this.medicineDoses.forEach(i -> i.setPrescription(null));
        }
        if (medicineDoses != null) {
            medicineDoses.forEach(i -> i.setPrescription(this));
        }
        this.medicineDoses = medicineDoses;
    }

    public Prescription medicineDoses(Set<MedicineDose> medicineDoses) {
        this.setMedicineDoses(medicineDoses);
        return this;
    }

    public Prescription addMedicineDose(MedicineDose medicineDose) {
        this.medicineDoses.add(medicineDose);
        medicineDose.setPrescription(this);
        return this;
    }

    public Prescription removeMedicineDose(MedicineDose medicineDose) {
        this.medicineDoses.remove(medicineDose);
        medicineDose.setPrescription(null);
        return this;
    }

    public Set<DiagnosticTest> getDiagnosticTests() {
        return this.diagnosticTests;
    }

    public void setDiagnosticTests(Set<DiagnosticTest> diagnosticTests) {
        if (this.diagnosticTests != null) {
            this.diagnosticTests.forEach(i -> i.setPrescription(null));
        }
        if (diagnosticTests != null) {
            diagnosticTests.forEach(i -> i.setPrescription(this));
        }
        this.diagnosticTests = diagnosticTests;
    }

    public Prescription diagnosticTests(Set<DiagnosticTest> diagnosticTests) {
        this.setDiagnosticTests(diagnosticTests);
        return this;
    }

    public Prescription addDiagnosticTest(DiagnosticTest diagnosticTest) {
        this.diagnosticTests.add(diagnosticTest);
        diagnosticTest.setPrescription(this);
        return this;
    }

    public Prescription removeDiagnosticTest(DiagnosticTest diagnosticTest) {
        this.diagnosticTests.remove(diagnosticTest);
        diagnosticTest.setPrescription(null);
        return this;
    }

    public DoctorVisit getDoctorVisitType() {
        return this.doctorVisitType;
    }

    public void setDoctorVisitType(DoctorVisit doctorVisit) {
        if (this.doctorVisitType != null) {
            this.doctorVisitType.setPrescription(null);
        }
        if (doctorVisit != null) {
            doctorVisit.setPrescription(this);
        }
        this.doctorVisitType = doctorVisit;
    }

    public Prescription doctorVisitType(DoctorVisit doctorVisit) {
        this.setDoctorVisitType(doctorVisit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prescription)) {
            return false;
        }
        return getId() != null && getId().equals(((Prescription) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prescription{" +
            "id=" + getId() +
            ", history='" + getHistory() + "'" +
            ", compliant='" + getCompliant() + "'" +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", bpHigh=" + getBpHigh() +
            ", bpLow=" + getBpLow() +
            ", temperature=" + getTemperature() +
            ", otherVital='" + getOtherVital() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
