package nl.top.assessment.bootstraptests;

import nl.top.assessment.bootstrap.CsvDataLoader;
import nl.top.assessment.model.CsvRecord;
import nl.top.assessment.repositories.CsvRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CsvDataLoaderTest {

    @Mock
    private CsvRecordRepository repository;

    @InjectMocks
    private CsvDataLoader csvDataLoader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadData() throws Exception {
        List<CsvRecord> expectedRecords = new ArrayList<>();
        CsvRecord record1 = new CsvRecord();
        record1.setSource("ZIB");
        record1.setCodeListCode("ZIB001");
        record1.setCode("271636001");
        record1.setDisplayValue("Polsslag regelmatig");
        record1.setLongDescription("The long description is necessary");
        record1.setFromDate(LocalDate.parse("01-01-2019", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        record1.setToDate(null);
        record1.setSortingPriority(1);
        expectedRecords.add(record1);

        for (CsvRecord record : expectedRecords) {
            when(repository.save(Mockito.any(CsvRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));
        }
        csvDataLoader.loadData();

        when(repository.findAll()).thenReturn(expectedRecords);

        List<CsvRecord> records = repository.findAll();
        assertThat(records).hasSize(expectedRecords.size());

        assertThat(records).anySatisfy(record -> {
            assertThat(record.getSource()).isEqualTo("ZIB");
            assertThat(record.getCodeListCode()).isEqualTo("ZIB001");
            assertThat(record.getCode()).isEqualTo("271636001");
            assertThat(record.getDisplayValue()).isEqualTo("Polsslag regelmatig");
            assertThat(record.getLongDescription()).isEqualTo("The long description is necessary");
            assertThat(record.getFromDate()).isEqualTo(LocalDate.parse("01-01-2019", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            assertThat(record.getToDate()).isNull();
            assertThat(record.getSortingPriority()).isEqualTo(1);
        });
    }
}

