package com.company.structureinventorysystem.utils.io;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringFileOperationTest {

    private final StringFileOperation sfo = new StringFileOperation();

    @Test
    public void shouldReadThrowIllegalArgumentExceptionWhenPathIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sfo.read(null));
        assertEquals("Path must not null or empty", exception.getMessage());
    }

    @Test
    public void shouldReadThrowIllegalArgumentExceptionWhenPathIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sfo.read(""));
        assertEquals("Path must not null or empty", exception.getMessage());
    }

    @Test
    public void shouldReadThrowRuntimeExceptionWhenPathNotFound() {
        //Given
        String path = "src/test/resources/none.txt";
        //Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sfo.read(path));
        assertEquals(String.format("Given path %s not found", path), exception.getMessage());
    }

    @Test
    public void shouldReadReturnEmptyListWhenFileIsEmptyToo() {
        assertEquals(0, sfo.read("src/test/resources/converter/empty.sql").size());
    }

    @Test
    public void shouldGReadReturnContentWhenNotEmptyFileExists() {
        //When
        List<String> userTestSample = sfo.read("src/test/resources/converter/user_test_sample.sql");
        List<String> userRolesTestSample = sfo.read("src/test/resources/converter/user_roles_test_sample.sql");
        List<String> buildingAuditTestSample = sfo.read("src/test/resources/converter/building_audit_test_sample.sql");
        //Then
        assertEquals(8, userTestSample.size());
        assertEquals("INSERT  INTO    structureinventorysystem.users(first_name, last_name)", userTestSample.get(0));
        assertEquals("VALUES ('John', 'Smith'),", userTestSample.get(1));
        assertEquals("(   'Lisa', 'Monk' ) ,", userTestSample.get(2));
        assertEquals("('Ann', 'McDonald' ),('Jonathan', 'Doe'),", userTestSample.get(3));
        assertEquals("('Luigi', 'Fresco') , ('Raul', 'Gonzales'),", userTestSample.get(4));
        assertEquals("('Marco', 'Bellucci'  ),", userTestSample.get(5));
        assertEquals("('Mark', 'Holmes'), (  'Amir', 'Khan'),", userTestSample.get(6));
        assertEquals("('Miriam', 'Velasques') ;", userTestSample.get(7));
        assertEquals(1, userRolesTestSample.size());
        assertEquals("INSERT INTO structureinventorysystem.user_roles(user_id, roles)VALUES(1, 'VISITOR') ,(2, 'USER'),(3, 'USER'),(4, 'USER'),(5, 'VISITOR'),(6, 'USER'),(7, 'VISITOR')   ,(8, 'USER'),(8, 'VISITOR'),(8, 'ADMIN'),(9, 'USER'),     (9, 'VISITOR'),(9, 'ADMIN') , (10, 'VISITOR');", userRolesTestSample.get(0));
        assertEquals(4, buildingAuditTestSample.size());
        assertEquals("INSERT INTO structureinventorysystem.building_audits (name, structure_type, location, description, created_on, created_by_user_id, updated_on, updated_by_user_id) VALUES ('Building audit inventory in Bytom', 'BUILDING', 'Bytom', 'Building audit in Bytom', NOW() + INTERVAL 5 DAY, 1, NOW() + INTERVAL 7 DAY, 5),", buildingAuditTestSample.get(0));
        assertEquals("('Building audit inventory in Ruda Slaska', 'BUILDING', 'Ruda Slaska', 'Building audit in Ruda Slaska', NOW() + INTERVAL 7 DAY, 3, NOW() + INTERVAL 10 DAY, 10)  ,('Building audit inventory in Katowice', 'BUILDING', 'Katowice', 'Building audit in Katowice', NOW() + INTERVAL 10 DAY, 4, NOW() + INTERVAL 15 DAY, 6),", buildingAuditTestSample.get(1));
        assertEquals("('Building audit inventory in Bierun', 'BUILDING', 'Bierun', 'Building audit in Bierun', NOW() + INTERVAL 16 DAY, 2, NOW() + INTERVAL 23 DAY, 8),  ('Building audit inventory in Jastrzebie Zdroj', 'BUILDING', 'Jastrzebie Zdroj', 'Building audit in Jastrzebie Zdroj', NOW() + INTERVAL 20 DAY, 3, NOW() + INTERVAL 21 DAY, 3),", buildingAuditTestSample.get(2));
        assertEquals("('Building audit inventory in Rydultowy', 'BUILDING', 'Rydultowy', 'Building audit in Rydultowy', NOW() + INTERVAL 20 DAY, 9, NOW() + INTERVAL 21 DAY, 1);", buildingAuditTestSample.get(3));
    }

}
