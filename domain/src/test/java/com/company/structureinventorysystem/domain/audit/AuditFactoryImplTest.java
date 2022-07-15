package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AuditFactoryImplTest {

    private final AuditFactory auditFactory = new AuditFactoryImpl();

    @Test
    public void shouldCreateAuditReturnBuildingAudit() {
        //When
        Audit audit = auditFactory.createAudit(StructureType.BUILDING, "Building Audit", null, new User("Mark", "Lizard"));
        //Then
        assertEquals("Building Audit", audit.getName());
        assertEquals(StructureType.BUILDING, audit.getStructureType());
        assertEquals(LocalDate.now(), audit.getCreatedOn());
        assertEquals("Mark", audit.getCreatedBy().getFirstName());
        assertEquals("Lizard", audit.getCreatedBy().getLastName());
        assertNull(audit.getDescription());
        assertNull(audit.getLocation());
        assertNull(audit.getUpdatedBy());
    }

    @Test
    public void shouldCreateAuditReturnPipelineAudit() {
        //When
        Audit audit = auditFactory.createAudit(StructureType.PIPELINE, "Pipeline Audit", LocalDate.parse("2000-01-01"), new User("Cathy", "Watson"));
        //Then
        assertEquals("Pipeline Audit", audit.getName());
        assertEquals(StructureType.PIPELINE, audit.getStructureType());
        assertEquals(LocalDate.parse("2000-01-01"), audit.getCreatedOn());
        assertEquals("Cathy", audit.getCreatedBy().getFirstName());
        assertEquals("Watson", audit.getCreatedBy().getLastName());
        assertNull(audit.getDescription());
        assertNull(audit.getLocation());
        assertNull(audit.getUpdatedBy());
    }

}
