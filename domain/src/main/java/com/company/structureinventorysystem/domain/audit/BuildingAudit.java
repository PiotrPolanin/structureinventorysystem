package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import com.company.structureinventorysystem.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "building_audits", schema = "structureinventorysystem")
public class BuildingAudit extends Audit implements UpdateEntity<BuildingAudit> {

    public BuildingAudit(@NotNull String name, @NotNull User createdBy) {
        super(name, createdBy);
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
            this.updatedOn = LocalDate.now();
            this.updatedBy = updated.getUpdatedBy();
        } else {
            throw new IllegalArgumentException(String.format("%s parameter cannot be null", this.getClass().getSimpleName()));
        }
    }

}
