package com.company.structureinventorysystem.domain.audit;

import com.company.structureinventorysystem.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineAuditTest {

    @Test
    public void shouldPipelineAuditBeProperlyCreated() {
        //Given
        PipelineAudit pipelineAudit = new PipelineAudit("Water Supply Pipeline Audit", new User("Christina", "West"));
        //When
        pipelineAudit.setLocation("Germany, Cottbus");
        pipelineAudit.setDescription("Water Supply Pipeline Audit for Cottbus city in Germany");
        pipelineAudit.setUpdatedBy(new User("Richard", "Smith"));
        //Then
        assertEquals("Water Supply Pipeline Audit", pipelineAudit.getName());
        assertEquals("Germany, Cottbus", pipelineAudit.getLocation());
        assertEquals("Water Supply Pipeline Audit for Cottbus city in Germany", pipelineAudit.getDescription());
        assertEquals("Christina", pipelineAudit.getCreatedBy().getFirstName());
        assertEquals("West", pipelineAudit.getCreatedBy().getLastName());
        assertEquals("Richard", pipelineAudit.getUpdatedBy().getFirstName());
        assertEquals("Smith", pipelineAudit.getUpdatedBy().getLastName());
    }

    @Test
    public void shouldUpdateThrowIllegalArgumentExceptionWhenParameterIsNull() {
        //Given
        PipelineAudit pipelineAudit = new PipelineAudit("Gas Supply Pipeline Audit", new User("Richard", "McDowell"));
        //Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> pipelineAudit.update(null));
        assertEquals("PipelineAudit parameter cannot be null", exception.getMessage());
    }

    @Test
    public void updateShouldReplaceValuesWhenParameterIsNotNull() {
        //Given
        PipelineAudit pipelineAudit_1 = new PipelineAudit("Pipeline Audit", new User("Luigi", "Conte"));
        PipelineAudit pipelineAudit_2 = new PipelineAudit("Updated Pipeline Audit", new User("Giovanni", "Bertuccio"));
        //When
        pipelineAudit_2.setLocation("Italy, Perugia");
        pipelineAudit_2.setDescription("Pipeline Audit for Perugia city in Italy");
        pipelineAudit_2.setUpdatedBy(new User("Roberto", "Rossi"));
        pipelineAudit_1.update(pipelineAudit_2);
        //Then
        assertEquals(pipelineAudit_1.getName(), pipelineAudit_2.getName());
        assertEquals(pipelineAudit_1.getLocation(), pipelineAudit_2.getLocation());
        assertEquals(pipelineAudit_1.getDescription(), pipelineAudit_2.getDescription());
        assertEquals(pipelineAudit_1.getCreatedBy().getFirstName(), "Luigi");
        assertEquals(pipelineAudit_1.getCreatedBy().getLastName(), "Conte");
        assertEquals(pipelineAudit_1.getUpdatedBy().getFirstName(), pipelineAudit_2.getUpdatedBy().getFirstName());
        assertEquals(pipelineAudit_1.getUpdatedBy().getLastName(), pipelineAudit_2.getUpdatedBy().getLastName());
    }
}
