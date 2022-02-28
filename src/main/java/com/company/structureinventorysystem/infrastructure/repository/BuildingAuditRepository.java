package com.company.structureinventorysystem.infrastructure.repository;

import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingAuditRepository extends GenericRepository<BuildingAudit> {
}
