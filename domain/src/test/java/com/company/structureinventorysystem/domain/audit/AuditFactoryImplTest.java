package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuditFactoryImplTest {

    private AuditFactory auditFactory = new AuditFactoryImpl();

    @Test
    public void shouldCreateAuditReturnBuildingAuditWhenParametersAreNull() {
        //When
        Audit audit = auditFactory.createAudit(StructureType.BUILDING, null, null);
        //Then
        assertNull(audit.getName());
        assertEquals(StructureType.BUILDING, audit.getStructureType());
        assertNull(audit.getCreatedBy());
    }

}
