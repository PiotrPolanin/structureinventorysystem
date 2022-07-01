package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.domain.shared.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Audit extends BaseEntity<Long> {

    @Column(nullable = false)
    @Size(min = 2, max = 255, message = "{validation.error.audit.name.Size}")
    @Pattern(regexp = "a-zA-z", message = "{validation.error.audit.name.Pattern}")
    protected String name;
    @Size(max = 255, message = "{validation.error.audit.location.Size}")
    @Pattern(regexp = "a-zA-z", message = "{validation.error.audit.location.Pattern}")
    protected String location;
    @Size(max = 255, message = "{validation.error.audit.description.Size}")
    @Pattern(regexp = "a-zA-z", message = "{validation.error.audit.description.Pattern}")
    protected String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "structure_type", nullable = false)
    protected StructureType structureType;
    @Column(name = "created_on", nullable = false, updatable = false)
    protected LocalDate createdOn;
    @Column(name = "updated_on")
    @Future(message = "{validation.error.audit.updatedOn.Future}")
    protected LocalDate updatedOn;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_audit_created_by_user_id"))
    protected User createdBy;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id", foreignKey = @ForeignKey(name = "FK_audit_updated_by_user_id"))
    protected User updatedBy;

    public Audit(@NotNull String name, @NotNull User createdBy) {
        this.name = name;
        this.createdOn = LocalDate.now();
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
        this.updatedBy = updatedBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
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
