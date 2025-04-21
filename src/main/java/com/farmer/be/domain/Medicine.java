package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.MedicineType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Medicine.
 */
@Entity
@Table(name = "medicine")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "generic_molecule")
    private String genericMolecule;

    @Column(name = "is_prescription_needed")
    private Boolean isPrescriptionNeeded;

    @Column(name = "description")
    private String description;

    @Column(name = "mrp")
    private Double mrp;

    @Column(name = "is_insurance_covered")
    private Boolean isInsuranceCovered;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MedicineType type;

    @Column(name = "is_consumable")
    private Boolean isConsumable;

    @Column(name = "is_returnable")
    private Boolean isReturnable;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prescription", "medicine" }, allowSetters = true)
    private Set<MedicineDose> medicineDoses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "med")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "med", "hospital", "admissions" }, allowSetters = true)
    private Set<MedicineBatch> medicineBatches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medicine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Medicine name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericMolecule() {
        return this.genericMolecule;
    }

    public Medicine genericMolecule(String genericMolecule) {
        this.setGenericMolecule(genericMolecule);
        return this;
    }

    public void setGenericMolecule(String genericMolecule) {
        this.genericMolecule = genericMolecule;
    }

    public Boolean getIsPrescriptionNeeded() {
        return this.isPrescriptionNeeded;
    }

    public Medicine isPrescriptionNeeded(Boolean isPrescriptionNeeded) {
        this.setIsPrescriptionNeeded(isPrescriptionNeeded);
        return this;
    }

    public void setIsPrescriptionNeeded(Boolean isPrescriptionNeeded) {
        this.isPrescriptionNeeded = isPrescriptionNeeded;
    }

    public String getDescription() {
        return this.description;
    }

    public Medicine description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMrp() {
        return this.mrp;
    }

    public Medicine mrp(Double mrp) {
        this.setMrp(mrp);
        return this;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Boolean getIsInsuranceCovered() {
        return this.isInsuranceCovered;
    }

    public Medicine isInsuranceCovered(Boolean isInsuranceCovered) {
        this.setIsInsuranceCovered(isInsuranceCovered);
        return this;
    }

    public void setIsInsuranceCovered(Boolean isInsuranceCovered) {
        this.isInsuranceCovered = isInsuranceCovered;
    }

    public MedicineType getType() {
        return this.type;
    }

    public Medicine type(MedicineType type) {
        this.setType(type);
        return this;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }

    public Boolean getIsConsumable() {
        return this.isConsumable;
    }

    public Medicine isConsumable(Boolean isConsumable) {
        this.setIsConsumable(isConsumable);
        return this;
    }

    public void setIsConsumable(Boolean isConsumable) {
        this.isConsumable = isConsumable;
    }

    public Boolean getIsReturnable() {
        return this.isReturnable;
    }

    public Medicine isReturnable(Boolean isReturnable) {
        this.setIsReturnable(isReturnable);
        return this;
    }

    public void setIsReturnable(Boolean isReturnable) {
        this.isReturnable = isReturnable;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Medicine isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Medicine createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Medicine createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Medicine updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Medicine updatedOn(Instant updatedOn) {
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
            this.medicineDoses.forEach(i -> i.setMedicine(null));
        }
        if (medicineDoses != null) {
            medicineDoses.forEach(i -> i.setMedicine(this));
        }
        this.medicineDoses = medicineDoses;
    }

    public Medicine medicineDoses(Set<MedicineDose> medicineDoses) {
        this.setMedicineDoses(medicineDoses);
        return this;
    }

    public Medicine addMedicineDose(MedicineDose medicineDose) {
        this.medicineDoses.add(medicineDose);
        medicineDose.setMedicine(this);
        return this;
    }

    public Medicine removeMedicineDose(MedicineDose medicineDose) {
        this.medicineDoses.remove(medicineDose);
        medicineDose.setMedicine(null);
        return this;
    }

    public Set<MedicineBatch> getMedicineBatches() {
        return this.medicineBatches;
    }

    public void setMedicineBatches(Set<MedicineBatch> medicineBatches) {
        if (this.medicineBatches != null) {
            this.medicineBatches.forEach(i -> i.setMed(null));
        }
        if (medicineBatches != null) {
            medicineBatches.forEach(i -> i.setMed(this));
        }
        this.medicineBatches = medicineBatches;
    }

    public Medicine medicineBatches(Set<MedicineBatch> medicineBatches) {
        this.setMedicineBatches(medicineBatches);
        return this;
    }

    public Medicine addMedicineBatch(MedicineBatch medicineBatch) {
        this.medicineBatches.add(medicineBatch);
        medicineBatch.setMed(this);
        return this;
    }

    public Medicine removeMedicineBatch(MedicineBatch medicineBatch) {
        this.medicineBatches.remove(medicineBatch);
        medicineBatch.setMed(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicine)) {
            return false;
        }
        return getId() != null && getId().equals(((Medicine) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medicine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", genericMolecule='" + getGenericMolecule() + "'" +
            ", isPrescriptionNeeded='" + getIsPrescriptionNeeded() + "'" +
            ", description='" + getDescription() + "'" +
            ", mrp=" + getMrp() +
            ", isInsuranceCovered='" + getIsInsuranceCovered() + "'" +
            ", type='" + getType() + "'" +
            ", isConsumable='" + getIsConsumable() + "'" +
            ", isReturnable='" + getIsReturnable() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
