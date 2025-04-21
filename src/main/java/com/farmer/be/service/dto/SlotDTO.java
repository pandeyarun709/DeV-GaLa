package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.Day;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.Slot} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SlotDTO implements Serializable {

    private Long id;

    private Day day;

    private Long startTimeHour;

    private Long startTimeMin;

    private Long endTimeHour;

    private Long endTimeMin;

    private Boolean isActive;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private DoctorVisitTypeDTO doctorVisitType;

    private DiagnosticTestDTO test;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Long getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(Long startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public Long getStartTimeMin() {
        return startTimeMin;
    }

    public void setStartTimeMin(Long startTimeMin) {
        this.startTimeMin = startTimeMin;
    }

    public Long getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(Long endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public Long getEndTimeMin() {
        return endTimeMin;
    }

    public void setEndTimeMin(Long endTimeMin) {
        this.endTimeMin = endTimeMin;
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

    public DoctorVisitTypeDTO getDoctorVisitType() {
        return doctorVisitType;
    }

    public void setDoctorVisitType(DoctorVisitTypeDTO doctorVisitType) {
        this.doctorVisitType = doctorVisitType;
    }

    public DiagnosticTestDTO getTest() {
        return test;
    }

    public void setTest(DiagnosticTestDTO test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SlotDTO)) {
            return false;
        }

        SlotDTO slotDTO = (SlotDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, slotDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SlotDTO{" +
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
            ", doctorVisitType=" + getDoctorVisitType() +
            ", test=" + getTest() +
            "}";
    }
}
