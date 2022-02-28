package com.company.structureinventorysystem.web;

import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import com.company.structureinventorysystem.domain.audit.BuildingAuditService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/audit/building")
public class BuildingAuditController extends GenericController<BuildingAudit> {

    public BuildingAuditController(BuildingAuditService service) {
        super(service);
    }
}
