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
import com.company.structureinventorysystem.web.configuration.WebConfig;
import com.company.structureinventorysystem.web.shared.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {PersistenceTestConfiguration.class, BuildingAuditPersistenceTestConfiguration.class, BuildingAuditControllerTestConfiguration.class, ConversionConfiguration.class, WebConfig.class})
public class BuildingAuditControllerTest {

    private static final String BUILDING_AUDIT_ENDPOINT = "/api/audit/building";
    private final BuildingAuditService buildingAuditService;
    private final UserService userService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;
    private static List<String> refBuildingAuditNames;
    private static List<String> refBuildingAuditLocations;
    private static List<String> refBuildingAuditDescriptions;
    private static List<LocalDate> refDatesOfCreation;
    private static List<LocalDate> refDatesOfUpdate;
    private static List<Integer> refUserIdsWhoCreatedBuildingAudits;
    private static List<Integer> refUserIdsWhoUpdatedBuildingAudits;

    @Autowired
    public BuildingAuditControllerTest(BuildingAuditService buildingAuditService, UserService userService, HttpMessageConverter httpMessageConverter, ObjectMapper objectMapper, MessageSource messageSource) {
        this.buildingAuditService = buildingAuditService;
        this.userService = userService;
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BuildingAuditController(buildingAuditService)).setMessageConverters(httpMessageConverter).setControllerAdvice(new GlobalExceptionHandler()).build();
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
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
        refUserIdsWhoCreatedBuildingAudits = extractor.extract(path, 5).stream().map(Integer::valueOf).collect(Collectors.toList());
        refUserIdsWhoUpdatedBuildingAudits = extractor.extract(path, 7).stream().map(Integer::valueOf).collect(Collectors.toList());
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
                .andExpect(jsonPath("$", hasSize(6)));
        for (int i = 0; i < bAuditsFromDB.size(); i++) {
            mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=id&dir=asc"))
                    .andExpect(jsonPath("$[" + i + "].id", is(bAuditsFromDB.get(i).getId().intValue())))
                    .andExpect(jsonPath("$[" + i + "].name", is(bAuditsFromDB.get(i).getName())))
                    .andExpect(jsonPath("$[" + i + "].structureType", is(bAuditsFromDB.get(i).getStructureType().toString())))
                    .andExpect(jsonPath("$[" + i + "].location", is(bAuditsFromDB.get(i).getLocation())))
                    .andExpect(jsonPath("$[" + i + "].description", is(bAuditsFromDB.get(i).getDescription())))
                    .andExpect(jsonPath("$[" + i + "].createdOn", is(bAuditsFromDB.get(i).getCreatedOn().toString())))
                    .andExpect(jsonPath("$[" + i + "].createdBy.id", is(bAuditsFromDB.get(i).getCreatedBy().getId().intValue())))
                    .andExpect(jsonPath("$[" + i + "].updatedOn", is(bAuditsFromDB.get(i).getUpdatedOn().toString())))
                    .andExpect(jsonPath("$[" + i + "].updatedBy.id", is(bAuditsFromDB.get(i).getUpdatedBy().getId().intValue())));
        }
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
        Integer[] refUserIdsWhoCreatedBuildingAuditsAsc = refUserIdsWhoCreatedBuildingAudits.stream().sorted().collect(Collectors.toList()).toArray(Integer[]::new);
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
        Integer[] refUserIdsWhoCreatedBuildingAuditsDesc = refUserIdsWhoCreatedBuildingAudits.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).toArray(Integer[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=createdBy&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].createdBy.id", contains(refUserIdsWhoCreatedBuildingAuditsDesc)));
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
        Integer[] refUserIdsWhoUpdatedBuildingAuditsAsc = refUserIdsWhoUpdatedBuildingAudits.stream().sorted().collect(Collectors.toList()).toArray(Integer[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=updatedBy&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].updatedBy.id", contains(refUserIdsWhoUpdatedBuildingAuditsAsc)));
    }

    @Test
    public void shouldGetReturnBuildingAuditsBodySortedByUpdatedByDescending() throws Exception {
        //Given
        Integer[] refUserIdsWhoUpdatedBuildingAuditsDesc = refUserIdsWhoUpdatedBuildingAudits.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).toArray(Integer[]::new);
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=updatedBy&dir=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[*].updatedBy.id", contains(refUserIdsWhoUpdatedBuildingAuditsDesc)));
    }

    @ParameterizedTest(name = "value")
    @ValueSource(strings = {"!", "a", "*", "\\", "+", "-"})
    public void shouldGetReturnErrorMessageWhenIdIsNotNumber(String value) throws Exception {
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "/" + value))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(("$"), is(String.format("Error to parse value to number for parameter %s", value))));
    }

    @Test
    public void shouldGetReturnErrorMessageWhenIdIsNotFound() throws Exception {
        //Given
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 25, "id", "asc");
        long nextId = buildingAudits.get(buildingAudits.size() - 1).getId() + 1;
        //Then
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "/" + nextId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(("$"), is(String.format("Entity com.company.structureinventorysystem.domain.audit.BuildingAudit with id %s not found", nextId))));
    }

    @Test
    public void shouldGetReturnBuildingAuditBodyWithExistingId() throws Exception {
        //Given
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 25, "id", "asc");
        //When
        for (int i = 0; i < buildingAudits.size(); i++) {
            mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "/" + (i + 1)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type", "application/json"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(buildingAudits.get(i).getId().intValue())))
                    .andExpect(jsonPath("$.name", is(buildingAudits.get(i).getName())))
                    .andExpect(jsonPath("$.structureType", is(buildingAudits.get(i).getStructureType().toString())))
                    .andExpect(jsonPath("$.location", is(buildingAudits.get(i).getLocation())))
                    .andExpect(jsonPath("$.description", is(buildingAudits.get(i).getDescription())))
                    .andExpect(jsonPath("$.createdOn", is(buildingAudits.get(i).getCreatedOn().toString())))
                    .andExpect(jsonPath("$.createdBy.id", is(buildingAudits.get(i).getCreatedBy().getId().intValue())))
                    .andExpect(jsonPath("$.updatedOn", is(buildingAudits.get(i).getUpdatedOn().toString())))
                    .andExpect(jsonPath("$.updatedBy.id", is(buildingAudits.get(i).getUpdatedBy().getId().intValue())));
        }
    }

    @ParameterizedTest(name = "value")
    @ValueSource(strings = {"~", "@", ".", "%", "^", "*", "=", "|", ">"})
    public void shouldDeleteReturnErrorMessageWhenIdIsInvalid(String value) throws Exception {
        mockMvc.perform(delete(BUILDING_AUDIT_ENDPOINT + "/" + value))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(String.format("Error to parse value to number for parameter %s", value))));
    }

    @Test
    public void shouldDeleteReturnErrorMessageWhenIdNotExists() throws Exception {
        //Given
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 50, "id", "asc");
        long nextId = buildingAudits.get(buildingAudits.size() - 1).getId() + 1;
        //Then
        mockMvc.perform(delete(BUILDING_AUDIT_ENDPOINT + "/" + nextId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format("Entity com.company.structureinventorysystem.domain.audit.BuildingAudit with id %s not found", nextId))));
    }

    @Test
    @Transactional
    public void shouldDeleteReturnNoContentWhenBuildingAuditWithGivenIdExists() throws Exception {
        //Given
        List<BuildingAudit> buildingAudits = buildingAuditService.getAll(0, 50, "id", "asc");
        //Then
        for (int i = 0; i < buildingAudits.size(); i++) {
            mockMvc.perform(delete(BUILDING_AUDIT_ENDPOINT + "/" + (i + 1)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }
        mockMvc.perform(get(BUILDING_AUDIT_ENDPOINT + "?pageNo=0&pageSize=100&sortBy=id&dir=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldCreateReturnErrorMessageWhenBodyIsEmpty() throws Exception {
        mockMvc.perform(post(BUILDING_AUDIT_ENDPOINT).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error validation message: structure type must not null;user must not null;name must not null"));
    }

}
