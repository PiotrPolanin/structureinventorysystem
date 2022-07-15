package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.domain.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineAuditTest {

    @Test
    public void shouldPipelineAuditBeProperlyCreated() {
        //Given
        PipelineAudit pipelineAudit = new PipelineAudit("Water Supply Pipeline Audit", new User("Christina", "West"));
        pipelineAudit.setLocation("Germany, Cottbus");
        pipelineAudit.setDescription("Water Supply Pipeline Audit for Cottbus city in Germany");
        pipelineAudit.setUpdatedBy(new User("Richard", "Smith"));
        //Then
        assertEquals("Water Supply Pipeline Audit", pipelineAudit.getName());
        assertEquals(StructureType.PIPELINE, pipelineAudit.getStructureType());
        assertEquals("Germany, Cottbus", pipelineAudit.getLocation());
        assertEquals("Water Supply Pipeline Audit for Cottbus city in Germany", pipelineAudit.getDescription());
        assertEquals(LocalDate.now(), pipelineAudit.getCreatedOn());
        assertEquals("Christina", pipelineAudit.getCreatedBy().getFirstName());
        assertEquals("West", pipelineAudit.getCreatedBy().getLastName());
        assertEquals(LocalDate.now(), pipelineAudit.getUpdatedOn());
        assertEquals("Richard", pipelineAudit.getUpdatedBy().getFirstName());
        assertEquals("Smith", pipelineAudit.getUpdatedBy().getLastName());
    }

    @Test
    public void shouldCreatedOnRepresentActualDateWhenDateParameterIsNull() {
        //Given
        PipelineAudit buildingAudit = new PipelineAudit("PA", null, new User("John", "Doe"));
        //Then
        assertEquals(buildingAudit.getCreatedOn(), LocalDate.now());
    }

    @Test
    public void shouldUpdateThrowIllegalArgumentExceptionWhenParameterIsNull() {
        //Given
        PipelineAudit pipelineAudit = new PipelineAudit("Gas Supply Pipeline Audit", new User("Richard", "McDowell"));
        //Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> pipelineAudit.update(null));
        assertEquals("Updated state of com.company.structureinventorysystem.domain.audit.PipelineAudit must not be null", exception.getMessage());
    }

    @Test
    public void updateShouldReplaceValuesWhenParameterIsNotNull() {
        //Given
        PipelineAudit pipelineAudit_1 = new PipelineAudit("Pipeline Audit", new User("Luigi", "Conte"));
        PipelineAudit pipelineAudit_2 = new PipelineAudit("Updated Pipeline Audit", new User("Giovanni", "Bertuccio"));
        pipelineAudit_2.setLocation("Italy, Perugia");
        pipelineAudit_2.setDescription("Pipeline Audit for Perugia city in Italy");
        pipelineAudit_2.setUpdatedBy(new User("Roberto", "Rossi"));
        //When
        pipelineAudit_1.update(pipelineAudit_2);
        //Then
        assertEquals(pipelineAudit_2.getName(), pipelineAudit_1.getName());
        assertEquals(pipelineAudit_2.getStructureType(), pipelineAudit_1.getStructureType());
        assertEquals(pipelineAudit_2.getLocation(), pipelineAudit_1.getLocation());
        assertEquals(pipelineAudit_2.getDescription(), pipelineAudit_1.getDescription());
        assertEquals(pipelineAudit_2.getCreatedOn(), pipelineAudit_1.getCreatedOn());
        assertEquals("Luigi", pipelineAudit_1.getCreatedBy().getFirstName());
        assertEquals("Conte", pipelineAudit_1.getCreatedBy().getLastName());
        assertEquals(pipelineAudit_2.getUpdatedOn(), pipelineAudit_1.getUpdatedOn());
        assertEquals(pipelineAudit_2.getUpdatedBy().getFirstName(), pipelineAudit_1.getUpdatedBy().getFirstName());
        assertEquals(pipelineAudit_2.getUpdatedBy().getLastName(), pipelineAudit_1.getUpdatedBy().getLastName());
    }

}
