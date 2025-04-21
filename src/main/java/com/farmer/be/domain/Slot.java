package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.Day;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Slot.
 */
@Entity
@Table(name = "slot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Slot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Day day;

    @Column(name = "start_time_hour")
    private Long startTimeHour;

    @Column(name = "start_time_min")
    private Long startTimeMin;

    @Column(name = "end_time_hour")
    private Long endTimeHour;

    @Column(name = "end_time_min")
    private Long endTimeMin;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "slots", "doctorVisits", "doctor" }, allowSetters = true)
    private DoctorVisitType doctorVisitType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "diagnosticTestReports", "slots", "department", "prescription" }, allowSetters = true)
    private DiagnosticTest test;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Slot id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return this.day;
    }

    public Slot day(Day day) {
        this.setDay(day);
        return this;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Long getStartTimeHour() {
        return this.startTimeHour;
    }

    public Slot startTimeHour(Long startTimeHour) {
        this.setStartTimeHour(startTimeHour);
        return this;
    }

    public void setStartTimeHour(Long startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public Long getStartTimeMin() {
        return this.startTimeMin;
    }

    public Slot startTimeMin(Long startTimeMin) {
        this.setStartTimeMin(startTimeMin);
        return this;
    }

    public void setStartTimeMin(Long startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public Long getEndTimeHour() {
        return this.endTimeHour;
    }

    public Slot endTimeHour(Long endTimeHour) {
        this.setEndTimeHour(endTimeHour);
        return this;
    }

    public void setEndTimeHour(Long endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public Long getEndTimeMin() {
        return this.endTimeMin;
    }

    public Slot endTimeMin(Long endTimeMin) {
        this.setEndTimeMin(endTimeMin);
        return this;
    }

    public void setEndTimeMin(Long endTimeMin) {
        this.endTimeMin = endTimeMin;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Slot isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Slot createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Slot createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Slot updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Slot updatedOn(Instant updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public DoctorVisitType getDoctorVisitType() {
        return this.doctorVisitType;
    }

    public void setDoctorVisitType(DoctorVisitType doctorVisitType) {
        this.doctorVisitType = doctorVisitType;
    }

    public Slot doctorVisitType(DoctorVisitType doctorVisitType) {
        this.setDoctorVisitType(doctorVisitType);
        return this;
    }

    public DiagnosticTest getTest() {
        return this.test;
    }

    public void setTest(DiagnosticTest diagnosticTest) {
        this.test = diagnosticTest;
    }

    public Slot test(DiagnosticTest diagnosticTest) {
        this.setTest(diagnosticTest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Slot)) {
            return false;
        }
        return getId() != null && getId().equals(((Slot) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Slot{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", startTimeHour=" + getStartTimeHour() +
            ", startTimeMin=" + getStartTimeMin() +
            ", endTimeHour=" + getEndTimeHour() +
            ", endTimeMin=" + getEndTimeMin() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
