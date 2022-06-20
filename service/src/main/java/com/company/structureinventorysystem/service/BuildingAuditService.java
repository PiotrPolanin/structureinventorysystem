package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import com.company.structureinventorysystem.infrastructure.repository.BuildingAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingAuditService extends GenericService<BuildingAudit> {

    @Autowired
    public BuildingAuditService(BuildingAuditRepository repository) {
        super(repository);
    }
}
