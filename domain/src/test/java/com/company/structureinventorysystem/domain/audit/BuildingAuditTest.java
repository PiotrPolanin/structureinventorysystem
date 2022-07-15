package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingAuditTest {

    @Test
    public void shouldBuildingAuditBeProperlyCreated() {
        //Given
        BuildingAudit buildingAudit = new BuildingAudit("Building Audit", LocalDate.parse("2002-03-10") , new User("Lisa", "Gerald"));
        buildingAudit.setLocation("The United Kingdom");
        buildingAudit.setDescription("description");
        buildingAudit.setUpdatedBy(new User("Brendan", "Perry"));
        //Then
        assertEquals("Building Audit", buildingAudit.getName());
        assertEquals(StructureType.BUILDING, buildingAudit.getStructureType());
        assertEquals("The United Kingdom", buildingAudit.getLocation());
        assertEquals("description", buildingAudit.getDescription());
        assertEquals(LocalDate.parse("2002-03-10"), buildingAudit.getCreatedOn());
        assertEquals("Lisa", buildingAudit.getCreatedBy().getFirstName());
        assertEquals("Gerald", buildingAudit.getCreatedBy().getLastName());
        assertEquals(LocalDate.now(), buildingAudit.getUpdatedOn());
        assertEquals("Brendan", buildingAudit.getUpdatedBy().getFirstName());
        assertEquals("Perry", buildingAudit.getUpdatedBy().getLastName());
    }

    @Test
    public void shouldCreatedOnRepresentActualDateWhenDateParameterIsNull() {
        //Given
        BuildingAudit buildingAudit = new BuildingAudit("BA", null, new User("John", "Doe"));
        //Then
        assertEquals(buildingAudit.getCreatedOn(), LocalDate.now());
    }

    @Test
    public void updateShouldThrowIllegalArgumentExceptionWhenParameterIsNull() {
        //Given
        BuildingAudit buildingAudit = new BuildingAudit("Building Audit", new User("John", "Butler"));
        //Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> buildingAudit.update(null));
        assertEquals("Updated state of com.company.structureinventorysystem.domain.audit.BuildingAudit must not be null", exception.getMessage());
    }

    @Test
    public void updateShouldReplaceValuesWhenParameterIsNotNull() {
        //Given
        BuildingAudit buildingAudit_1 = new BuildingAudit("Building Audit", new User("Peter", "Hook"));
        BuildingAudit buildingAudit_2 = new BuildingAudit("Updated Building Audit", LocalDate.parse("2021-01-01"), new User("Louise", "Cash"));
        buildingAudit_2.setLocation("England - Somersby, Lincolnshire");
        buildingAudit_2.setDescription("Audit for historic buildings in Somersby (Lincolnshire, England)");
        buildingAudit_2.setUpdatedBy(new User("Ann", "Queen"));
        //When
        buildingAudit_1.update(buildingAudit_2);
        //Then
        assertEquals(buildingAudit_2.getName(), buildingAudit_1.getName());
        assertEquals(buildingAudit_2.getStructureType(), buildingAudit_1.getStructureType());
        assertEquals(buildingAudit_2.getLocation(), buildingAudit_1.getLocation());
        assertEquals(buildingAudit_2.getDescription(), buildingAudit_1.getDescription());
        assertNotEquals(buildingAudit_2.getCreatedOn(), buildingAudit_1.getCreatedOn());
        assertEquals("Peter", buildingAudit_1.getCreatedBy().getFirstName());
        assertEquals("Hook", buildingAudit_1.getCreatedBy().getLastName());
        assertEquals(buildingAudit_2.getUpdatedOn(), buildingAudit_1.getUpdatedOn());
        assertEquals(buildingAudit_2.getUpdatedBy().getFirstName(), buildingAudit_1.getUpdatedBy().getFirstName());
        assertEquals(buildingAudit_2.getUpdatedBy().getLastName(), buildingAudit_1.getUpdatedBy().getLastName());
    }

}
