package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.user.User;

public interface AuditFactory {
    Audit createAudit(StructureType type, String name, User createdBy);
}
