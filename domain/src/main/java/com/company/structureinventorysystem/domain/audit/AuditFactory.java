package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;

import javax.validation.constraints.NotNull;

public interface AuditFactory {
    Audit createAudit(@NotNull StructureType type, @NotNull String name, @NotNull User createdBy);
}
