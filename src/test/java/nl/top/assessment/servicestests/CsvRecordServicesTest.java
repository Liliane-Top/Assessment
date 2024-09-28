package nl.top.assessment.servicestests;

import nl.top.assessment.model.CsvRecord;
import nl.top.assessment.repositories.CsvRecordRepository;
import nl.top.assessment.services.CsvRecordServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class CsvRecordServicesTest {

    @InjectMocks
    private CsvRecordServicesImpl service;

    @Mock
    private CsvRecordRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByCodeSuccess() {
        CsvRecord record = new CsvRecord();
        record.setSource("ZIB");
        record.setCodeListCode("ZIB001");
        record.setCode("27163001");
        record.setDisplayValue("Test Display");
        record.setLongDescription("Test Description");
        record.setFromDate(LocalDate.of(2019, 1, 1));
        record.setToDate(LocalDate.of(2020, 1, 1));
        record.setSortingPriority(1);

        when(repository.findByCode("27163001")).thenReturn(Optional.of(record));

        Optional<CsvRecord> result = service.findByCode("27163001");

        assertTrue(result.isPresent(), "Record should be present");
        assertEquals("27163001", result.get().getCode(), "Code should match");
        assertEquals("Test Description", result.get().getLongDescription(), "Long description should match");
    }

    @Test
    public void testFindByCodeNotFound() {
        when(repository.findByCode("invalid_code")).thenReturn(Optional.empty());

        Optional<CsvRecord> result = service.findByCode("invalid_code");

        assertTrue(result.isEmpty(), "Record should not be present");
    }
}

