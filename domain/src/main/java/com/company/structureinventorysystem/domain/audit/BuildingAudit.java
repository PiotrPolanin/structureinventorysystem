package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import com.company.structureinventorysystem.domain.user.User;
import org.hibernate.annotations.Check;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "building_audits", schema = "structureinventorysystem")
@Check(constraints = "created_on <= updated_on")
public class BuildingAudit extends Audit implements UpdateEntity<BuildingAudit> {

    public BuildingAudit(@NotNull String name, @NotNull User createdBy) {
        super(name, createdBy);
        this.structureType = StructureType.BUILDING;
    }

    public BuildingAudit(@NotNull String name, @NotNull LocalDate createdOn, @NotNull User createdBy) {
        super(name, createdOn, createdBy);
        this.structureType = StructureType.BUILDING;
    }

    public BuildingAudit() {
    }

    @Override
    public void update(BuildingAudit updated) {
        if (updated != null) {
            this.name = updated.getName();
            this.location = updated.getLocation();
            this.description = updated.getDescription();
            setUpdatedBy(updated.getUpdatedBy());
        } else {
            throw new IllegalArgumentException(String.format("Updated state of %s must not be null", this.getClass().getName()));
        }
    }

}
