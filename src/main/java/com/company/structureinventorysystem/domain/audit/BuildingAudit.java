package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import com.company.structureinventorysystem.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "building_audits", schema = "structureinventorysystem")
public class BuildingAudit extends Audit implements UpdateEntity<BuildingAudit> {

    public BuildingAudit(String name, User createdBy) {
        super(name, createdBy);
        this.structureType = StructureType.BUILDING;
    }

    public BuildingAudit() {
    }

    @Override
    public void update(BuildingAudit updatedAudit) {
        if (updatedAudit != null) {
            if (this.structureType.equals(updatedAudit.structureType)) {
                this.name = updatedAudit.getName();
                this.location = updatedAudit.getLocation();
                this.description = updatedAudit.getDescription();
                this.updatedOn = LocalDate.now();
                this.updatedBy = updatedAudit.getUpdatedBy();
            } else {
                throw new IllegalArgumentException(String.format("Source class %s and value %s %s are different from updated class %s and value %s %s", this.getClass().getSimpleName(), this.getStructureType().getClass().getSimpleName(), this.getStructureType(), updatedAudit.getClass().getSimpleName(), updatedAudit.getStructureType().getClass().getSimpleName(), updatedAudit.getStructureType()));
            }
        } else {
            throw new IllegalArgumentException(String.format("%s parameter cannot be null", this.getClass().getSimpleName()));
        }
    }

}
