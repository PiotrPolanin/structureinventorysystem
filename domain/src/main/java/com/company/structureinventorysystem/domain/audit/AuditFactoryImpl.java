package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AuditFactoryImpl implements AuditFactory {

    @Override
    public Audit createAudit(@NotNull StructureType type, @NotNull String name, @NotNull LocalDate createdOn, @NotNull User createdBy) {
        switch (type) {
            case BUILDING:
                return new BuildingAudit(name, createdOn, createdBy);
            case PIPELINE:
                return new PipelineAudit(name, createdOn, createdBy);
            default:
                throw new IllegalArgumentException(String.format("Not supported audit for structure type %s", type));
        }
    }

}
