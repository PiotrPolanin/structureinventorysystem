package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingAuditTest {

    @Test
    public void shouldBuildingAuditBeProperlyCreated() {
        //Given
        BuildingAudit buildingAudit = new BuildingAudit("Building Audit", new User("Lisa", "Gerald"));
        //When
        buildingAudit.setLocation("The United Kingdom");
        buildingAudit.setDescription("description");
        buildingAudit.setUpdatedBy(new User("Brendan", "Perry"));
        //Then
        assertEquals("Building Audit", buildingAudit.getName());
        assertEquals(StructureType.BUILDING, buildingAudit.getStructureType());
        assertEquals("The United Kingdom", buildingAudit.getLocation());
        assertEquals("description", buildingAudit.getDescription());
        assertEquals("Lisa", buildingAudit.getCreatedBy().getFirstName());
        assertEquals("Gerald", buildingAudit.getCreatedBy().getLastName());
        assertEquals("Brendan", buildingAudit.getUpdatedBy().getFirstName());
        assertEquals("Perry", buildingAudit.getUpdatedBy().getLastName());
    }

    @Test
    public void updateShouldThrowIllegalArgumentExceptionWhenParameterIsNull() {
        //Given
        BuildingAudit buildingAudit = new BuildingAudit("Building Audit", new User("John", "Butler"));
        //Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> buildingAudit.update(null));
        assertEquals("BuildingAudit parameter cannot be null", exception.getMessage());
    }

    @Test
    public void updateShouldReplaceValuesWhenParameterIsNotNull() {
        //Given
        BuildingAudit buildingAudit_1 = new BuildingAudit("Building Audit", new User("Peter", "Hook"));
        BuildingAudit buildingAudit_2 = new BuildingAudit("Updated Building Audit", new User("Louise", "Cash"));
        //When
        buildingAudit_2.setLocation("England - Somersby, Lincolnshire");
        buildingAudit_2.setDescription("Audit for historic buildings in Somersby (Lincolnshire, England)");
        buildingAudit_2.setUpdatedBy(new User("Ann", "Queen"));
        buildingAudit_1.update(buildingAudit_2);
        //Then
        assertEquals(buildingAudit_1.getName(), buildingAudit_2.getName());
        assertEquals(buildingAudit_1.getLocation(), buildingAudit_2.getLocation());
        assertEquals(buildingAudit_1.getDescription(), buildingAudit_2.getDescription());
        assertEquals(buildingAudit_1.getCreatedBy().getFirstName(), "Peter");
        assertEquals(buildingAudit_1.getCreatedBy().getLastName(), "Hook");
        assertEquals(buildingAudit_1.getUpdatedBy().getFirstName(), buildingAudit_2.getUpdatedBy().getFirstName());
        assertEquals(buildingAudit_1.getUpdatedBy().getLastName(), buildingAudit_2.getUpdatedBy().getLastName());
    }

}
