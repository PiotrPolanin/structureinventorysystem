package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.user.User;

public class AuditFactoryImpl implements AuditFactory {

    @Override
    public Audit createAudit(StructureType type, String name, User createdBy) {
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
