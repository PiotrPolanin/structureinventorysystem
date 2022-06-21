package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.shared.UpdateEntity;
import com.company.structureinventorysystem.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "pipeline_audits", schema = "structureinventorysystem")
public class PipelineAudit extends Audit implements UpdateEntity<PipelineAudit> {

    public PipelineAudit(String name, User createdBy) {
        super(name, createdBy);
        this.structureType = StructureType.PIPELINE;
    }

    public PipelineAudit() {
    }

    @Override
    public void update(PipelineAudit updated) {
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
