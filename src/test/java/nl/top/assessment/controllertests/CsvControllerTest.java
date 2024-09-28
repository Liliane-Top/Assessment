package nl.top.assessment.controllertests;

import nl.top.assessment.controllers.CsvController;
import nl.top.assessment.mappers.CsvRecordMapper;
import nl.top.assessment.model.CsvRecord;
import nl.top.assessment.services.CsvRecordServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CsvController.class)
public class CsvControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CsvRecordServices service;

    @Mock
    private CsvRecordMapper csvRecordMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CsvController(service, csvRecordMapper)).build();
    }


    @Test
    public void testGetByCodeSuccess() throws Exception {
        CsvRecord record = new CsvRecord();
        record.setSource("ZIB");
        record.setCodeListCode("ZIB001");
        record.setCode("27163001");
        record.setDisplayValue("Test Display");
        record.setLongDescription("Test Description");
        record.setFromDate(LocalDate.of(2019, 1, 1));
        record.setToDate(LocalDate.of(2020, 1, 1));
        record.setSortingPriority(1);

        Mockito.when(service.findByCode("27163001")).thenReturn(Optional.of(record));

        mockMvc.perform(get("/api/csv/27163001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("27163001"))
                .andExpect(jsonPath("$.longDescription").value("Test Description"));
    }

    @Test
    public void testGetByCodeNotFound() throws Exception {
        Mockito.when(service.findByCode("invalid_code")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/csv/invalid_code"))
                .andExpect(status().isNotFound());
    }
}
