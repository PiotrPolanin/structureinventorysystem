package com.company.structureinventorysystem.tests;

import com.company.structureinventorysystem.domain.audit.Audit;
import com.company.structureinventorysystem.domain.audit.AuditFactoryImpl;
import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import com.company.structureinventorysystem.domain.audit.StructureType;
import com.company.structureinventorysystem.domain.user.User;

public class AuditFactoryImplTest {
    public static void main(String[] args) {
        AuditFactoryImpl auditFactory = new AuditFactoryImpl();
        Audit buildingAudit = auditFactory.createAudit(StructureType.BUILDING, "Building inventory", new User("Mike", "Patton"));
        Audit pipelineAudit = auditFactory.createAudit(StructureType.PIPELINE, "Pipeline inventory", new User("Mike", "Patton"));

        BuildingAudit updatedBuildingAudit = (BuildingAudit) auditFactory.createAudit(StructureType.BUILDING, "trick", new User("Jannet", "Hacker"));
//        updatedBuildingAudit.setStructureType(StructureType.PIPELINE);
        BuildingAudit buildingAudit2 = (BuildingAudit) buildingAudit;
        buildingAudit2.update(updatedBuildingAudit);
        System.out.println(buildingAudit);
        System.out.println(pipelineAudit);
        System.out.println(updatedBuildingAudit);
        System.out.println(buildingAudit2);
    }
}
