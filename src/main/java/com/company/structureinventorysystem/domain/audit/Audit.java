package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.user.User;
import com.company.structureinventorysystem.domain.shared.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Audit extends BaseEntity<Long> {

    @NotNull
    @Column(nullable = false)
    protected String name;
    protected String location;
    protected String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "structure_type", nullable = false)
    protected StructureType structureType;
    protected LocalDate createdOn;
    protected LocalDate updatedOn;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", unique = true)
    protected User createdBy;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id", unique = true)
    protected User updatedBy;

    public Audit(String name, User createdBy) {
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
                ", updated on: " + updatedOn +
                ", created by: " + createdBy +
                ", updated by: " + updatedBy + "]";
    }

}
