package com.company.structureinventorysystem.service;

import com.company.structureinventorysystem.configuration.BuildingAuditPersistenceTestConfiguration;
import com.company.structureinventorysystem.configuration.BuildingAuditServiceTestConfiguration;
import com.company.structureinventorysystem.configuration.PersistenceTestConfiguration;
import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import com.company.structureinventorysystem.domain.shared.StructureType;
import com.company.structureinventorysystem.utils.converter.SqlScriptExtractor;
import com.company.structureinventorysystem.utils.io.StringFileOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {PersistenceTestConfiguration.class, BuildingAuditPersistenceTestConfiguration.class, BuildingAuditServiceTestConfiguration.class})
public class BuildingAuditServiceTest {

    @Autowired
    private BuildingAuditService buildingAuditService;
    private static List<Long> refBuildingAuditIds;
    private static List<String> refBuildingAuditNames;
    private static List<String> refBuildingAuditLocations;
    private static List<String> refBuildingAuditDescriptions;
    private static List<LocalDate> refDatesOfCreation;
    private static List<LocalDate> refDatesOfUpdate;
    private static List<Long> refUserIdsWhoCreatedBuildingAudits;
    private static List<Long> refUserIdsWhoUpdatedBuildingAudits;

    @BeforeAll
    public static void setup() {
        SqlScriptExtractor extractor = new SqlScriptExtractor(new StringFileOperation());
        refBuildingAuditIds = List.of(1L, 2L, 3L, 4L, 5L, 6L);
        refDatesOfCreation = List.of(LocalDate.now().plusDays(5), LocalDate.now().plusDays(7), LocalDate.now().plusDays(10), LocalDate.now().plusDays(16), LocalDate.now().plusDays(20), LocalDate.now().plusDays(20));
        refDatesOfUpdate = List.of(LocalDate.now().plusDays(7), LocalDate.now().plusDays(10), LocalDate.now().plusDays(15), LocalDate.now().plusDays(23), LocalDate.now().plusDays(21), LocalDate.now().plusDays(21));
        refBuildingAuditNames = extractor.extract("src/test/resources/building_audit_test_sample.sql", 0);
        refBuildingAuditLocations = extractor.extract("src/test/resources/building_audit_test_sample.sql", 2);
        refBuildingAuditDescriptions = extractor.extract("src/test/resources/building_audit_test_sample.sql", 3);
        refUserIdsWhoCreatedBuildingAudits = extractor.extract("src/test/resources/building_audit_test_sample.sql", 5).stream().map(v -> Long.valueOf(v)).collect(Collectors.toList());
        refUserIdsWhoUpdatedBuildingAudits = extractor.extract("src/test/resources/building_audit_test_sample.sql", 7).stream().map(v -> Long.valueOf(v)).collect(Collectors.toList());
    }

    @Test
    public void shouldGetByIdThrowInvalidDataAccessApiUsageExceptionWhenIdIsNull() {
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> buildingAuditService.getById(null));
        assertEquals("The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!", exception.getMessage());
    }

    @Test
    public void shouldGetByIdThrowEntityNotFoundExceptionWhenIdNotFound() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> buildingAuditService.getById(500L));
        assertEquals("Entity com.company.structureinventorysystem.domain.audit.BuildingAudit with id 500 not found", exception.getMessage());
    }

    @Test
    public void shouldGetByIdReturnBuildingAuditFromDB() {
        //When
        BuildingAudit buildingAudit_1 = buildingAuditService.getById(1L);
        BuildingAudit buildingAudit_2 = buildingAuditService.getById(2L);
        BuildingAudit buildingAudit_3 = buildingAuditService.getById(3L);
        BuildingAudit buildingAudit_4 = buildingAuditService.getById(4L);
        BuildingAudit buildingAudit_5 = buildingAuditService.getById(5L);
        BuildingAudit buildingAudit_6 = buildingAuditService.getById(6L);
        //Then
        assertEquals(1, buildingAudit_1.getId());
        assertEquals(refBuildingAuditNames.get(0), buildingAudit_1.getName());
        assertEquals(StructureType.BUILDING, buildingAudit_1.getStructureType());
        assertEquals(refBuildingAuditLocations.get(0), buildingAudit_1.getLocation());
        assertEquals(refBuildingAuditDescriptions.get(0), buildingAudit_1.getDescription());
        assertEquals(LocalDate.now().plusDays(5), buildingAudit_1.getCreatedOn());
        assertEquals(refUserIdsWhoCreatedBuildingAudits.get(0), buildingAudit_1.getCreatedBy().getId());
        assertEquals(LocalDate.now().plusDays(7), buildingAudit_1.getUpdatedOn());
        assertEquals(refUserIdsWhoUpdatedBuildingAudits.get(0), buildingAudit_1.getUpdatedBy().getId());

        assertEquals(2, buildingAudit_2.getId());
        assertEquals(refBuildingAuditNames.get(1), buildingAudit_2.getName());
        assertEquals(StructureType.BUILDING, buildingAudit_2.getStructureType());
        assertEquals(refBuildingAuditLocations.get(1), buildingAudit_2.getLocation());
        assertEquals(refBuildingAuditDescriptions.get(1), buildingAudit_2.getDescription());
        assertEquals(LocalDate.now().plusDays(7), buildingAudit_2.getCreatedOn());
        assertEquals(refUserIdsWhoCreatedBuildingAudits.get(1), buildingAudit_2.getCreatedBy().getId());
        assertEquals(LocalDate.now().plusDays(10), buildingAudit_2.getUpdatedOn());
        assertEquals(refUserIdsWhoUpdatedBuildingAudits.get(1), buildingAudit_2.getUpdatedBy().getId());

        assertEquals(3, buildingAudit_3.getId());
        assertEquals(refBuildingAuditNames.get(2), buildingAudit_3.getName());
        assertEquals(StructureType.BUILDING, buildingAudit_3.getStructureType());
        assertEquals(refBuildingAuditLocations.get(2), buildingAudit_3.getLocation());
        assertEquals(refBuildingAuditDescriptions.get(2), buildingAudit_3.getDescription());
        assertEquals(LocalDate.now().plusDays(10), buildingAudit_3.getCreatedOn());
        assertEquals(refUserIdsWhoCreatedBuildingAudits.get(2), buildingAudit_3.getCreatedBy().getId());
        assertEquals(LocalDate.now().plusDays(15), buildingAudit_3.getUpdatedOn());
        assertEquals(refUserIdsWhoUpdatedBuildingAudits.get(2), buildingAudit_3.getUpdatedBy().getId());

        assertEquals(4, buildingAudit_4.getId());
        assertEquals(refBuildingAuditNames.get(3), buildingAudit_4.getName());
        assertEquals(StructureType.BUILDING, buildingAudit_4.getStructureType());
        assertEquals(refBuildingAuditLocations.get(3), buildingAudit_4.getLocation());
        assertEquals(refBuildingAuditDescriptions.get(3), buildingAudit_4.getDescription());
        assertEquals(LocalDate.now().plusDays(16), buildingAudit_4.getCreatedOn());
        assertEquals(refUserIdsWhoCreatedBuildingAudits.get(3), buildingAudit_4.getCreatedBy().getId());
        assertEquals(LocalDate.now().plusDays(23), buildingAudit_4.getUpdatedOn());
        assertEquals(refUserIdsWhoUpdatedBuildingAudits.get(3), buildingAudit_4.getUpdatedBy().getId());

        assertEquals(5, buildingAudit_5.getId());
        assertEquals(refBuildingAuditNames.get(4), buildingAudit_5.getName());
        assertEquals(StructureType.BUILDING, buildingAudit_5.getStructureType());
        assertEquals(refBuildingAuditLocations.get(4), buildingAudit_5.getLocation());
        assertEquals(refBuildingAuditDescriptions.get(4), buildingAudit_5.getDescription());
        assertEquals(LocalDate.now().plusDays(20), buildingAudit_5.getCreatedOn());
        assertEquals(refUserIdsWhoCreatedBuildingAudits.get(4), buildingAudit_5.getCreatedBy().getId());
        assertEquals(LocalDate.now().plusDays(21), buildingAudit_5.getUpdatedOn());
        assertEquals(refUserIdsWhoUpdatedBuildingAudits.get(4), buildingAudit_5.getUpdatedBy().getId());

        assertEquals(6, buildingAudit_6.getId());
        assertEquals(refBuildingAuditNames.get(5), buildingAudit_6.getName());
        assertEquals(StructureType.BUILDING, buildingAudit_6.getStructureType());
        assertEquals(refBuildingAuditLocations.get(5), buildingAudit_6.getLocation());
        assertEquals(refBuildingAuditDescriptions.get(5), buildingAudit_6.getDescription());
        assertEquals(LocalDate.now().plusDays(20), buildingAudit_6.getCreatedOn());
        assertEquals(refUserIdsWhoCreatedBuildingAudits.get(5), buildingAudit_6.getCreatedBy().getId());
        assertEquals(LocalDate.now().plusDays(21), buildingAudit_6.getUpdatedOn());
        assertEquals(refUserIdsWhoUpdatedBuildingAudits.get(5), buildingAudit_6.getUpdatedBy().getId());
    }

    @Test
    public void shouldGetAllThrowIllegalArgumentExceptionWhenParametersAreNull() {
        IllegalArgumentException exceptionForNullPageNo = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(null, 20, "id", "asc"));
        assertEquals("Each parameter must not null or empty", exceptionForNullPageNo.getMessage());
        IllegalArgumentException exceptionForNullPageSize = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(2, null, "name", "desc"));
        assertEquals("Each parameter must not null or empty", exceptionForNullPageSize.getMessage());
        IllegalArgumentException exceptionForNullSortBy = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(0, 50, null, "desc"));
        assertEquals("Each parameter must not null or empty", exceptionForNullSortBy.getMessage());
        IllegalArgumentException exceptionForNullDirectionSorting = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(0, 100, "description", null));
        assertEquals("Each parameter must not null or empty", exceptionForNullDirectionSorting.getMessage());
    }

    @Test
    public void shouldGetAllThrowIllegalArgumentExceptionWhenStringParametersAreEmpty() {
        IllegalArgumentException exceptionForEmptySortBy = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(5, 10, "", "desc"));
        assertEquals("Each parameter must not null or empty", exceptionForEmptySortBy.getMessage());
        IllegalArgumentException exceptionForEmptyDirectionSorting = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(0, 5, "structureType", ""));
        assertEquals("Each parameter must not null or empty", exceptionForEmptyDirectionSorting.getMessage());
    }

    @Test
    public void shouldGetAllThrowIllegalArgumentExceptionWhenPageNumberIsLessThanZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(-25, 0, "id", "asc"));
        assertEquals("Page index must not be less than zero!", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 0})
    public void shouldGetAllThrowIllegalArgumentExceptionWhenPageSizeIsLessThanOne(int pageSize) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> buildingAuditService.getAll(0, pageSize, "id", "desc"));
        assertEquals("Page size must not be less than one!", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"id:", "NAME", "LoCaTiOn", "desc", "structure_type", "Created_on", "UPDATEDon", "created+by", "updatedby"})
    public void shouldGetAllThrowPropertyReferenceExceptionWhenSortParametersAreNotValid(String fieldName) {
        assertThrows(PropertyReferenceException.class, () -> buildingAuditService.getAll(0, 100, fieldName, "asc"));
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByIdAscending() {
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 50, "id", "asc");
        List<Long> buildingAuditIdsInAscOrder = buildingAudits.stream().map(ba -> ba.getId()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refBuildingAuditIds, buildingAuditIdsInAscOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByIdDescending() {
        //Given
        List<Long> refIdsInDescOrder = refBuildingAuditIds.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 50, "id", "desc");
        List<Long> buildingAuditIdsInDescOrder = buildingAudits.stream().map(ba -> ba.getId()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refIdsInDescOrder, buildingAuditIdsInDescOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByNameAscending() {
        //Given
        List<String> refBuildingAuditNamesInAscOrder = refBuildingAuditNames.stream().sorted().collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 10, "name", "asc");
        List<String> buildingAuditNamesInAscOrder = buildingAudits.stream().map(ba -> ba.getName()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refBuildingAuditNamesInAscOrder, buildingAuditNamesInAscOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByNameDescending() {
        //Given
        List<String> refBuildingAuditNamesInDescOrder = refBuildingAuditNames.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 15, "name", "desc");
        List<String> buildingAuditNamesInDescOrder = buildingAudits.stream().map(ba -> ba.getName()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refBuildingAuditNamesInDescOrder, buildingAuditNamesInDescOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByLocationAscending() {
        //Given
        List<String> refBuildingAuditLocationsInAscOrder = refBuildingAuditLocations.stream().sorted().collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 10, "location", "asc");
        List<String> buildingAuditLocationsInAscOrder = buildingAudits.stream().map(ba -> ba.getLocation()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refBuildingAuditLocationsInAscOrder, buildingAuditLocationsInAscOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByLocationDescending() {
        //Given
        List<String> refBuildingAuditLocationsInDescOrder = refBuildingAuditLocations.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 15, "location", "desc");
        List<String> buildingAuditLocationsInDescOrder = buildingAudits.stream().map(ba -> ba.getLocation()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refBuildingAuditLocationsInDescOrder, buildingAuditLocationsInDescOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByDescriptionAscending() {
        //Given
        List<String> refBuildingAuditDescriptionsInAscOrder = refBuildingAuditDescriptions.stream().sorted().collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 10, "description", "asc");
        List<String> buildingAuditDescriptionsInAscOrder = buildingAudits.stream().map(ba -> ba.getDescription()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refBuildingAuditDescriptionsInAscOrder, buildingAuditDescriptionsInAscOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByDescriptionDescending() {
        //Given
        List<String> refBuildingAuditDescriptionsInDescOrder = refBuildingAuditDescriptions.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 15, "description", "desc");
        List<String> buildingAuditDescriptionsInDescOrder = buildingAudits.stream().map(ba -> ba.getDescription()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refBuildingAuditDescriptionsInDescOrder, buildingAuditDescriptionsInDescOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByCreatedOnAscending() {
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 15, "createdOn", "asc");
        List<LocalDate> datesInAscOrder = buildingAudits.stream().map(ba -> ba.getCreatedOn()).collect(Collectors.toList());
        //Then
        assertEquals(6, datesInAscOrder.size());
        assertIterableEquals(refDatesOfCreation, datesInAscOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByCreatedOnDescending() {
        //Given
        List<LocalDate> refDatesOfCreationInDescOrder = refDatesOfCreation.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 15, "createdOn", "desc");
        List<LocalDate> datesInDescOrder = buildingAudits.stream().map(ba -> ba.getCreatedOn()).collect(Collectors.toList());
        //Then
        assertEquals(6, datesInDescOrder.size());
        assertIterableEquals(refDatesOfCreationInDescOrder, datesInDescOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByCreatedByAscending() {
        //Given
        List<Long> refUserIdsWhoCreatedBuildingAuditsInAscOrder = refUserIdsWhoCreatedBuildingAudits.stream().sorted().collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 10, "createdBy", "asc");
        List<Long> userIdsWhoCreatedBuildingAuditsInAscOrder = buildingAudits.stream().map(ba -> ba.getCreatedBy().getId()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refUserIdsWhoCreatedBuildingAuditsInAscOrder, userIdsWhoCreatedBuildingAuditsInAscOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByCreatedByDescending() {
        //Given
        List<Long> refUserIdsWhoCreatedBuildingAuditsInDescOrder = refUserIdsWhoCreatedBuildingAudits.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 10, "createdBy", "desc");
        List<Long> userIdsWhoCreatedBuildingAuditsInDescOrder = buildingAudits.stream().map(ba -> ba.getCreatedBy().getId()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refUserIdsWhoCreatedBuildingAuditsInDescOrder, userIdsWhoCreatedBuildingAuditsInDescOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByUpdatedOnAscending() {
        //Given
        List<LocalDate> refDatesOfUpdateInAscOrders = refDatesOfUpdate.stream().sorted().collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 30, "updatedBy", "asc");
        List<LocalDate> datesOfUpdateInAscOrders = buildingAudits.stream().map(ba -> ba.getUpdatedOn()).collect(Collectors.toList());
        //Then
        assertEquals(6, datesOfUpdateInAscOrders.size());
        assertIterableEquals(refDatesOfUpdateInAscOrders, datesOfUpdateInAscOrders);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByUpdatedOnDescending() {
        //Given
        List<LocalDate> refDatesOfUpdateInDescOrders = refDatesOfUpdate.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 30, "updatedBy", "desc");
        List<LocalDate> datesOfUpdateInDescOrders = buildingAudits.stream().map(ba -> ba.getUpdatedOn()).collect(Collectors.toList());
        //Then
        assertEquals(6, datesOfUpdateInDescOrders.size());
        assertIterableEquals(refDatesOfUpdateInDescOrders, datesOfUpdateInDescOrders);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByUpdatedByAscending() {
        //Given
        List<Long> refUserIdsWhoUpdatedBuildingAuditsInAscOrder = refUserIdsWhoUpdatedBuildingAudits.stream().sorted().collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 10, "updatedBy", "asc");
        List<Long> userIdsWhoUpdatedBuildingAuditsInAscOrder = buildingAudits.stream().map(ba -> ba.getUpdatedBy().getId()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refUserIdsWhoUpdatedBuildingAuditsInAscOrder, userIdsWhoUpdatedBuildingAuditsInAscOrder);
    }

    @Test
    public void shouldGetAllReturnBuildingAuditsSortedByUpdatedByDescending() {
        //Given
        List<Long> refUserIdsWhoUpdatedBuildingAuditsInDescOrder = refUserIdsWhoUpdatedBuildingAudits.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //When
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 10, "updatedBy", "desc");
        List<Long> userIdsWhoUpdatedBuildingAuditsInDescOrder = buildingAudits.stream().map(ba -> ba.getUpdatedBy().getId()).collect(Collectors.toList());
        //Then
        assertIterableEquals(refUserIdsWhoUpdatedBuildingAuditsInDescOrder, userIdsWhoUpdatedBuildingAuditsInDescOrder);
    }

}
