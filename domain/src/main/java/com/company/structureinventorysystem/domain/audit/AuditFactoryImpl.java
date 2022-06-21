package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;

import javax.validation.constraints.NotNull;

public class AuditFactoryImpl implements AuditFactory {

    @Override
    public Audit createAudit(@NotNull StructureType type, @NotNull String name, @NotNull User createdBy) {
        switch (type) {
            case BUILDING:
                return new BuildingAudit(name, createdBy);
            case PIPELINE:
                return new PipelineAudit(name, createdBy);
            default:
                throw new IllegalArgumentException("Structure type is not supported");
        }
    }

}
