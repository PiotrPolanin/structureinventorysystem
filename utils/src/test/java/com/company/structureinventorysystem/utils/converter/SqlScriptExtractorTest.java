package com.company.structureinventorysystem.utils.converter;

import com.company.structureinventorysystem.utils.io.StringFileOperation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SqlScriptExtractorTest {

    private final SqlScriptExtractor extractor = new SqlScriptExtractor(new StringFileOperation());

    @Test
    public void shouldGetReturnContentWhenNotEmptyFileExists() {
        //When
        List<String> userTestSample = extractor.get("src/test/resources/converter/user_test_sample.sql");
        List<String> userRolesTestSample = extractor.get("src/test/resources/converter/user_roles_test_sample.sql");
        List<String> buildingAuditTestSample = extractor.get("src/test/resources/converter/building_audit_test_sample.sql");
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

    @Test
    public void shouldExtractThrowIllegalArgumentExceptionWhenAttributeIndexIsLessThanZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> extractor.extract("src/test/resources/building_audit_test_tables.sql", -5));
        assertEquals("Attribute index must not be less than 0", exception.getMessage());
    }

    @Test
    public void shouldExtractThrowIllegalArgumentExceptionWhenAttributeIndexIsOutOfRange() {
        IllegalArgumentException exceptionForDBSample_1 = assertThrows(IllegalArgumentException.class, () -> extractor.extract("src/test/resources/converter/user_test_sample.sql", 2));
        assertEquals("Attribute index 2 is out of range. Last attributes array index is 1", exceptionForDBSample_1.getMessage());
        IllegalArgumentException exceptionForDBSample_2 = assertThrows(IllegalArgumentException.class, () -> extractor.extract("src/test/resources/converter/user_roles_test_sample.sql", 2));
        assertEquals("Attribute index 2 is out of range. Last attributes array index is 1", exceptionForDBSample_2.getMessage());
        IllegalArgumentException exceptionForDBSample_3 = assertThrows(IllegalArgumentException.class, () -> extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 8));
        assertEquals("Attribute index 8 is out of range. Last attributes array index is 7", exceptionForDBSample_3.getMessage());
    }


    @Test
    public void shouldExtractSuccessfulStoreDataWithValidAttributeIndex() {
        //When
        List<String> usersFirstNames = extractor.extract("src/test/resources/converter/user_test_sample.sql", 0);
        List<String> usersLastNames = extractor.extract("src/test/resources/converter/user_test_sample.sql", 1);
        List<String> usersIds = extractor.extract("src/test/resources/converter/user_roles_test_sample.sql", 0);
        List<String> usersRoles = extractor.extract("src/test/resources/converter/user_roles_test_sample.sql", 1);

        List<String> buildingAuditNames = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 0);
        List<String> structureTypes = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 1);
        List<String> locations = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 2);
        List<String> descriptions = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 3);
        List<String> datesOfCreation = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 4);
        List<String> usersIdsResponsibleForCreationOfAudits = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 5);
        List<String> datesOfUpdate = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 6);
        List<String> usersIdsResponsibleForUpdateOfAudits = extractor.extract("src/test/resources/converter/building_audit_test_sample.sql", 7);
        //Then
        assertEquals(10, usersFirstNames.size());
        assertIterableEquals(List.of("John", "Lisa", "Ann", "Jonathan", "Luigi", "Raul", "Marco", "Mark", "Amir", "Miriam"), usersFirstNames);
        assertEquals(10, usersLastNames.size());
        assertIterableEquals(List.of("Smith", "Monk", "McDonald", "Doe", "Fresco", "Gonzales", "Bellucci", "Holmes", "Khan", "Velasques"), usersLastNames);
        assertEquals(14, usersIds.size());
        assertIterableEquals(List.of("1", "2", "3", "4", "5", "6", "7", "8", "8", "8", "9", "9", "9", "10"), usersIds);
        assertEquals(14, usersRoles.size());
        assertIterableEquals(List.of("VISITOR", "USER", "USER", "USER", "VISITOR", "USER", "VISITOR", "USER", "VISITOR", "ADMIN", "USER", "VISITOR", "ADMIN", "VISITOR"), usersRoles);

        assertEquals(6, buildingAuditNames.size());
        assertIterableEquals(List.of("Building audit inventory in Bytom", "Building audit inventory in Ruda Slaska", "Building audit inventory in Katowice", "Building audit inventory in Bierun", "Building audit inventory in Jastrzebie Zdroj", "Building audit inventory in Rydultowy"), buildingAuditNames);
        assertEquals(6, structureTypes.size());
        assertIterableEquals(List.of("BUILDING", "BUILDING", "BUILDING", "BUILDING", "BUILDING", "BUILDING"), structureTypes);
        assertEquals(6, locations.size());
        assertIterableEquals(List.of("Bytom", "Ruda Slaska", "Katowice", "Bierun", "Jastrzebie Zdroj", "Rydultowy"), locations);
        assertEquals(6, descriptions.size());
        assertIterableEquals(List.of("Building audit in Bytom", "Building audit in Ruda Slaska", "Building audit in Katowice", "Building audit in Bierun", "Building audit in Jastrzebie Zdroj", "Building audit in Rydultowy"), descriptions);
        assertEquals(6, datesOfCreation.size());
        assertIterableEquals(List.of("NOW() + INTERVAL 5 DAY", "NOW() + INTERVAL 7 DAY", "NOW() + INTERVAL 10 DAY", "NOW() + INTERVAL 16 DAY", "NOW() + INTERVAL 20 DAY", "NOW() + INTERVAL 20 DAY"), datesOfCreation);
        assertEquals(6, usersIdsResponsibleForCreationOfAudits.size());
        assertIterableEquals(List.of("1", "3", "4", "2", "3", "9"), usersIdsResponsibleForCreationOfAudits);
        assertEquals(6, datesOfUpdate.size());
        assertIterableEquals(List.of("NOW() + INTERVAL 7 DAY", "NOW() + INTERVAL 10 DAY", "NOW() + INTERVAL 15 DAY", "NOW() + INTERVAL 23 DAY", "NOW() + INTERVAL 21 DAY", "NOW() + INTERVAL 21 DAY"), datesOfUpdate);
        assertEquals(6, usersIdsResponsibleForUpdateOfAudits.size());
        assertIterableEquals(List.of("5", "10", "6", "8", "3", "1"), usersIdsResponsibleForUpdateOfAudits);
    }

}
