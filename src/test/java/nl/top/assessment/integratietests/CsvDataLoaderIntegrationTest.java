package nl.top.assessment.integratietests;

import nl.top.assessment.bootstrap.CsvDataLoader;
import nl.top.assessment.model.CsvRecord;
import nl.top.assessment.repositories.CsvRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(false) // Voorkom dat de gegevens worden teruggedraaid na de test
public class CsvDataLoaderIntegrationTest {

    @Autowired
    private CsvDataLoader csvDataLoader;

    @Autowired
    private CsvRecordRepository repository;

    @Test
    public void testLoadData() {
        csvDataLoader.loadData();

        List<CsvRecord> records = repository.findAll();
        assertThat(records).isNotEmpty();

        assertThat(records).anySatisfy(record -> {
            assertThat(record.getSource()).isEqualTo("ZIB");
            assertThat(record.getCodeListCode()).isEqualTo("ZIB001");
            assertThat(record.getCode()).isEqualTo("271636001");
            assertThat(record.getDisplayValue()).isEqualTo("Polsslag regelmatig");
            assertThat(record.getLongDescription()).isEqualTo("The long description is necessary");
        });
    }
}
