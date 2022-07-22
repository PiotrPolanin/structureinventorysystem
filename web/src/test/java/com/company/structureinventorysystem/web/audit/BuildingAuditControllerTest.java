package com.company.structureinventorysystem.web.audit;

import com.company.structureinventorysystem.configuration.BuildingAuditPersistenceTestConfiguration;
import com.company.structureinventorysystem.configuration.PersistenceTestConfiguration;
import com.company.structureinventorysystem.domain.audit.BuildingAudit;
import com.company.structureinventorysystem.service.BuildingAuditService;
import com.company.structureinventorysystem.service.UserService;
import com.company.structureinventorysystem.utils.converter.SqlScriptExtractor;
import com.company.structureinventorysystem.utils.io.StringFileOperation;
import com.company.structureinventorysystem.web.configuration.BuildingAuditControllerTestConfiguration;
import com.company.structureinventorysystem.web.configuration.ConversionConfiguration;
import com.company.structureinventorysystem.web.shared.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {PersistenceTestConfiguration.class, BuildingAuditPersistenceTestConfiguration.class, BuildingAuditControllerTestConfiguration.class, ConversionConfiguration.class})
public class BuildingAuditControllerTest {

    private static final String BUILDING_AUDIT_ENDPOINT = "/api/audit/building";
    private final BuildingAuditService buildingAuditService;
    private final UserService userService;
    private final MockMvc mockMvc;
    private static List<String> refBuildingAuditNames;
    private static List<String> refBuildingAuditLocations;
    private static List<String> refBuildingAuditDescriptions;
    private static List<LocalDate> refDatesOfCreation;
    private static List<LocalDate> refDatesOfUpdate;
    private static List<Long> refUserIdsWhoCreatedBuildingAudits;
    private static List<Long> refUserIdsWhoUpdatedBuildingAudits;
//    private static int[] refUserIdsWhoUpdatedBuildingAudits;

    @Autowired
    public BuildingAuditControllerTest(BuildingAuditService buildingAuditService, UserService userService, HttpMessageConverter httpMessageConverter) {
        this.buildingAuditService = buildingAuditService;
        this.userService = userService;
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BuildingAuditController(buildingAuditService)).setMessageConverters(httpMessageConverter).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @BeforeAll
    public static void setup() {
        String path = ClassLoader.getSystemResource("building_audit_test_sample.sql").getPath().substring(1);
        SqlScriptExtractor extractor = new SqlScriptExtractor(new StringFileOperation());
        refDatesOfCreation = List.of(LocalDate.now().plusDays(5), LocalDate.now().plusDays(3), LocalDate.now().plusDays(10), LocalDate.now().plusDays(9), LocalDate.now().plusDays(20), LocalDate.now().plusDays(20));
        refDatesOfUpdate = List.of(LocalDate.now().plusDays(7), LocalDate.now().plusDays(10), LocalDate.now().plusDays(15), LocalDate.now().plusDays(23), LocalDate.now().plusDays(21), LocalDate.now().plusDays(21));
        refBuildingAuditNames = extractor.extract(path, 0);
        refBuildingAuditLocations = extractor.extract(path, 2);
        refBuildingAuditDescriptions = extractor.extract(path, 3);
        refUserIdsWhoCreatedBuildingAudits = extractor.extract(path, 5).stream().map(Long::valueOf).collect(Collectors.toList());
        refUserIdsWhoUpdatedBuildingAudits = extractor.extract(path, 7).stream().map(Long::valueOf).collect(Collectors.toList());
//        refUserIdsWhoUpdatedBuildingAudits = extractor.extract(path, 7).stream().mapToInt(Integer::parseInt).toArray();
    }

    @Test
    public void shouldGetReturnErrorMessageWhenParametersAreNull() throws Exception {
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is("Each parameter must not be null")));
    }

    @Test
    public void shouldGetReturnErrorMessageWhenDirParameterIsEmpty() throws Exception {
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=50&sortBy=name&dir="))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is("Parameter sortBy and dir must not be empty")));
    }

    @Test
    public void shouldGetSetDefaultValueWhenPageNoParameterIsNotSet() throws Exception {
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=&pageSize=2&sortBy=id&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(6)))
                .andExpect(jsonPath("$[1].id", is(5)));
    }

    @Test
    public void shouldGetSetDefaultValueWhenPageSizeParameterIsNotSet() throws Exception {
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=&sortBy=id&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodyWithOkStatus() throws Exception {
        //Given
        List<BuildingAudit> bAuditsFromDB = buildingAuditService.getAll(0, 100, "id", "asc");
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=id&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id", is(bAuditsFromDB.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(bAuditsFromDB.get(0).getName())))
                .andExpect(jsonPath("$[0].structureType", is(bAuditsFromDB.get(0).getStructureType().toString())))
                .andExpect(jsonPath("$[0].location", is(bAuditsFromDB.get(0).getLocation())))
                .andExpect(jsonPath("$[0].description", is(bAuditsFromDB.get(0).getDescription())))
                .andExpect(jsonPath("$[0].createdOn", is(bAuditsFromDB.get(0).getCreatedOn().toString())))
                .andExpect(jsonPath("$[0].createdBy.id", is(bAuditsFromDB.get(0).getCreatedBy().getId().intValue())))
                .andExpect(jsonPath("$[0].updatedOn", is(bAuditsFromDB.get(0).getUpdatedOn().toString())))
                .andExpect(jsonPath("$[0].updatedBy.id", is(bAuditsFromDB.get(0).getUpdatedBy().getId().intValue())))
                .andExpect(jsonPath("$[1].id", is(bAuditsFromDB.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(bAuditsFromDB.get(1).getName())))
                .andExpect(jsonPath("$[1].structureType", is(bAuditsFromDB.get(1).getStructureType().toString())))
                .andExpect(jsonPath("$[1].location", is(bAuditsFromDB.get(1).getLocation())))
                .andExpect(jsonPath("$[1].description", is(bAuditsFromDB.get(1).getDescription())))
                .andExpect(jsonPath("$[1].createdOn", is(bAuditsFromDB.get(1).getCreatedOn().toString())))
                .andExpect(jsonPath("$[1].createdBy.id", is(bAuditsFromDB.get(1).getCreatedBy().getId().intValue())))
                .andExpect(jsonPath("$[1].updatedOn", is(bAuditsFromDB.get(1).getUpdatedOn().toString())))
                .andExpect(jsonPath("$[1].updatedBy.id", is(bAuditsFromDB.get(1).getUpdatedBy().getId().intValue())))
                .andExpect(jsonPath("$[2].id", is(bAuditsFromDB.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].name", is(bAuditsFromDB.get(2).getName())))
                .andExpect(jsonPath("$[2].structureType", is(bAuditsFromDB.get(2).getStructureType().toString())))
                .andExpect(jsonPath("$[2].location", is(bAuditsFromDB.get(2).getLocation())))
                .andExpect(jsonPath("$[2].description", is(bAuditsFromDB.get(2).getDescription())))
                .andExpect(jsonPath("$[2].createdOn", is(bAuditsFromDB.get(2).getCreatedOn().toString())))
                .andExpect(jsonPath("$[2].createdBy.id", is(bAuditsFromDB.get(2).getCreatedBy().getId().intValue())))
                .andExpect(jsonPath("$[2].updatedOn", is(bAuditsFromDB.get(2).getUpdatedOn().toString())))
                .andExpect(jsonPath("$[2].updatedBy.id", is(bAuditsFromDB.get(2).getUpdatedBy().getId().intValue())))
                .andExpect(jsonPath("$[3].id", is(bAuditsFromDB.get(3).getId().intValue())))
                .andExpect(jsonPath("$[3].name", is(bAuditsFromDB.get(3).getName())))
                .andExpect(jsonPath("$[3].structureType", is(bAuditsFromDB.get(3).getStructureType().toString())))
                .andExpect(jsonPath("$[3].location", is(bAuditsFromDB.get(3).getLocation())))
                .andExpect(jsonPath("$[3].description", is(bAuditsFromDB.get(3).getDescription())))
                .andExpect(jsonPath("$[3].createdOn", is(bAuditsFromDB.get(3).getCreatedOn().toString())))
                .andExpect(jsonPath("$[3].createdBy.id", is(bAuditsFromDB.get(3).getCreatedBy().getId().intValue())))
                .andExpect(jsonPath("$[3].updatedOn", is(bAuditsFromDB.get(3).getUpdatedOn().toString())))
                .andExpect(jsonPath("$[3].updatedBy.id", is(bAuditsFromDB.get(3).getUpdatedBy().getId().intValue())))
                .andExpect(jsonPath("$[4].id", is(bAuditsFromDB.get(4).getId().intValue())))
                .andExpect(jsonPath("$[4].name", is(bAuditsFromDB.get(4).getName())))
                .andExpect(jsonPath("$[4].structureType", is(bAuditsFromDB.get(4).getStructureType().toString())))
                .andExpect(jsonPath("$[4].location", is(bAuditsFromDB.get(4).getLocation())))
                .andExpect(jsonPath("$[4].description", is(bAuditsFromDB.get(4).getDescription())))
                .andExpect(jsonPath("$[4].createdOn", is(bAuditsFromDB.get(4).getCreatedOn().toString())))
                .andExpect(jsonPath("$[4].createdBy.id", is(bAuditsFromDB.get(4).getCreatedBy().getId().intValue())))
                .andExpect(jsonPath("$[4].updatedOn", is(bAuditsFromDB.get(4).getUpdatedOn().toString())))
                .andExpect(jsonPath("$[4].updatedBy.id", is(bAuditsFromDB.get(4).getUpdatedBy().getId().intValue())))
                .andExpect(jsonPath("$[5].id", is(bAuditsFromDB.get(5).getId().intValue())))
                .andExpect(jsonPath("$[5].name", is(bAuditsFromDB.get(5).getName())))
                .andExpect(jsonPath("$[5].structureType", is(bAuditsFromDB.get(5).getStructureType().toString())))
                .andExpect(jsonPath("$[5].location", is(bAuditsFromDB.get(5).getLocation())))
                .andExpect(jsonPath("$[5].description", is(bAuditsFromDB.get(5).getDescription())))
                .andExpect(jsonPath("$[5].createdOn", is(bAuditsFromDB.get(5).getCreatedOn().toString())))
                .andExpect(jsonPath("$[5].createdBy.id", is(bAuditsFromDB.get(5).getCreatedBy().getId().intValue())))
                .andExpect(jsonPath("$[5].updatedOn", is(bAuditsFromDB.get(5).getUpdatedOn().toString())))
                .andExpect(jsonPath("$[5].updatedBy.id", is(bAuditsFromDB.get(5).getUpdatedBy().getId().intValue())));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodyDefaultSortedByIdAscending() throws Exception {
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].id", contains(1, 2, 3, 4, 5, 6)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodyDefaultSortedByIdDescending() throws Exception {
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=50&sortBy=&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].id", contains(6, 5, 4, 3, 2, 1)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByNameAscending() throws Exception {
        //Given
        String[] refBuildingAuditNamesAsc = refBuildingAuditNames.stream().sorted().toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=name&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].name", contains(refBuildingAuditNamesAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByNameDescending() throws Exception {
        //Given
        String[] refBuildingAuditNamesDesc = refBuildingAuditNames.stream().sorted(Comparator.reverseOrder()).toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=name&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].name", contains(refBuildingAuditNamesDesc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByLocationAscending() throws Exception {
        //Given
        String[] refBuildingAuditLocationsAsc = refBuildingAuditLocations.stream().sorted().toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=location&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].location", contains(refBuildingAuditLocationsAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByLocationDescending() throws Exception {
        //Given
        String[] refBuildingAuditLocationsDesc = refBuildingAuditLocations.stream().sorted(Comparator.reverseOrder()).toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=location&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].location", contains(refBuildingAuditLocationsDesc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByDescriptionAscending() throws Exception {
        //Given
        String[] refBuildingAuditDescriptionsAsc = refBuildingAuditDescriptions.stream().sorted().toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=description&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].description", contains(refBuildingAuditDescriptionsAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByDescriptionDescending() throws Exception {
        //Given
        String[] refBuildingAuditDescriptionsDesc = refBuildingAuditDescriptions.stream().sorted(Comparator.reverseOrder()).toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=description&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].description", contains(refBuildingAuditDescriptionsDesc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByCreatedOnAscending() throws Exception {
        //Given
        String[] refDatesOfCreationAsc = refDatesOfCreation.stream().sorted().map(LocalDate::toString).toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=createdOn&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].createdOn", contains(refDatesOfCreationAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByCreatedOnDescending() throws Exception {
        //Given
        String[] refDatesOfCreationDesc = refDatesOfCreation.stream().sorted(Comparator.reverseOrder()).map(LocalDate::toString).toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=createdOn&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].createdOn", contains(refDatesOfCreationDesc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByCreatedByAscending() throws Exception {
        //Given
        int[] refUserIdsWhoCreatedBuildingAuditsAsc = refUserIdsWhoCreatedBuildingAudits.stream().sorted().mapToInt(l -> l.intValue()).toArray();
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=createdBy&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].createdBy.id", contains(refUserIdsWhoCreatedBuildingAuditsAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByCreatedByDescending() throws Exception {
        //Given
        String[] refUserIdsWhoCreatedBuildingAuditsDesc = refUserIdsWhoCreatedBuildingAudits.stream().sorted().toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=createdBy&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].createdBy", contains(refUserIdsWhoCreatedBuildingAuditsDesc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByUpdatedOnAscending() throws Exception {
        //Given
        String[] refDatesOfUpdateAsc = refDatesOfUpdate.stream().sorted().map(LocalDate::toString).toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=updatedOn&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].updatedOn", contains(refDatesOfUpdateAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByUpdatedOnDescending() throws Exception {
        //Given
        String[] refDatesOfUpdateDesc = refDatesOfUpdate.stream().sorted(Comparator.reverseOrder()).map(LocalDate::toString).toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=updatedOn&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].updatedOn", contains(refDatesOfUpdateDesc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByUpdatedByAscending() throws Exception {
        //Given
        long[] refUserIdsWhoUpdatedBuildingAuditsAsc = refUserIdsWhoUpdatedBuildingAudits.stream().sorted().mapToLong(l -> l).toArray();
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=updatedBy&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].updatedBy", contains(refUserIdsWhoUpdatedBuildingAuditsAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByUpdatedByDescending() throws Exception {
        //Given
        String[] refUserIdsWhoUpdatedBuildingAuditsDesc = refUserIdsWhoUpdatedBuildingAudits.stream().sorted().toArray(String[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=UpdatedBy&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].UpdatedBy", contains(refUserIdsWhoUpdatedBuildingAuditsDesc)));
    }

}
