package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuditFactoryImplTest {

    private AuditFactory auditFactory = new AuditFactoryImpl();

    @Test
    public void shouldCreateAuditReturnBuildingAudit() {
        //When
        Audit audit = auditFactory.createAudit(StructureType.BUILDING, "Building Audit", new User("Mark", "Lizard"));
        //Then
        assertEquals("Building Audit", audit.getName());
        assertEquals(StructureType.BUILDING, audit.getStructureType());
        assertEquals("Mark", audit.getCreatedBy().getFirstName());
        assertEquals("Lizard", audit.getCreatedBy().getLastName());
    }

    @Test
    public void shouldCreateAuditReturnPipelineAudit() {
        //When
        Audit audit = auditFactory.createAudit(StructureType.PIPELINE, "Pipeline Audit", new User("Cathy", "Watson"));
        //Then
        assertEquals("Pipeline Audit", audit.getName());
        assertEquals(StructureType.PIPELINE, audit.getStructureType());
        assertEquals("Cathy", audit.getCreatedBy().getFirstName());
        assertEquals("Watson", audit.getCreatedBy().getLastName());
    }

}
