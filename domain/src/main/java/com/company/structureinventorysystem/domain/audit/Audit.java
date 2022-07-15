package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.domain.shared.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Audit extends BaseEntity<Long> {

    @NotNull(message = "{validation.error.audit.name.NotNull}")
    @Size(min = 2, max = 255, message = "{validation.error.audit.name.Size}")
    @Column(nullable = false)
    protected String name;
    @Size(max = 255, message = "{validation.error.audit.location.Size}")
    protected String location;
    @Size(max = 255, message = "{validation.error.audit.description.Size}")
    protected String description;
    @NotNull(message = "{validation.error.audit.structureType.NotNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "structure_type", nullable = false)
    protected StructureType structureType;
    @NotNull(message = "{validation.error.audit.createdOn.NotNull}")
    @Column(name = "created_on", nullable = false, updatable = false)
    protected LocalDate createdOn = LocalDate.now();
    @Column(name = "updated_on")
    protected LocalDate updatedOn;
    @NotNull(message = "{validation.error.audit.createdBy.NotNull}")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_audit_created_by_user_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "firstName", "lastName", "academicDegree", "roles"})
    protected User createdBy;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id", foreignKey = @ForeignKey(name = "FK_audit_updated_by_user_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "firstName", "lastName", "academicDegree", "roles"})
    protected User updatedBy;

    public Audit(@NotNull String name, @NotNull User createdBy) {
        this.name = name;
        this.createdBy = createdBy;
    }

    public Audit(@NotNull String name, @NotNull LocalDate createdOn, @NotNull User createdBy) {
        this.name = name;
        if (createdOn != null) {
            this.createdOn = createdOn;
        }
        this.createdBy = createdBy;
    }

    public Audit() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StructureType getStructureType() {
        return structureType;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setUpdatedBy(User updatedBy) {
        if (updatedBy != null) {
            this.updatedBy = updatedBy;
            if (updatedOn == null) {
                this.updatedOn = LocalDate.now();
            }
        }
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public boolean isUpdatedBeforeCreatedOn() {
        if (createdOn != null && updatedOn != null) {
            return updatedOn.isBefore(createdOn);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Audit [" +
                "id: " + id +
                ", name: " + name +
                ", location: " + location +
                ", description: " + description +
                ", structure type: " + structureType +
                ", created on: " + createdOn +
                ", created by: " + createdBy +
                ", updated on: " + updatedOn +
                ", updated by: " + updatedBy + "]";
    }

}
