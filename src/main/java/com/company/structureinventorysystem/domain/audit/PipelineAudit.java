package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pipeline_audits", schema = "structureinventorysystem")
public class PipelineAudit extends Audit {

    public PipelineAudit(String name, User createdBy) {
        super(name, createdBy);
        this.structureType = StructureType.PIPELINE;
    }

    public PipelineAudit() {
    }
}
