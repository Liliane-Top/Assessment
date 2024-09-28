package nl.top.assessment.bootstrap;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import nl.top.assessment.model.CsvRecord;
import nl.top.assessment.repositories.CsvRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CsvDataLoader {

    private final CsvRecordRepository repository;


    @Autowired
    public CsvDataLoader(CsvRecordRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadData() {
        try (CSVReader reader = new CSVReader(new InputStreamReader(
                getClass().getResourceAsStream("/exercise.csv"), StandardCharsets.UTF_8))) {

            String[] line;
            List<CsvRecord> records = new ArrayList<>();

            while ((line = reader.readNext()) != null) {
                if (checkIsHeader(line)) continue;
                CsvRecord record = parseCsvLine(line);
                addIfNotADuplicate(record, records);
            }
            repository.saveAll(records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkIsHeader(String[] line) {
        if (line.length == 0 || line[0].equals("code")) {
            return true;
        }
        return false;
    }

    private void addIfNotADuplicate(CsvRecord record, List<CsvRecord> records) {
        if (!repository.existsByCode(record.getCode())) {
            records.add(record);
        } else {
            log.warn("Duplicaat record gevonden voor code: {}", record.getCode());
        }
    }

    private CsvRecord parseCsvLine(String[] line) {
        CsvRecord record = new CsvRecord();

        record.setSource(line[0]);
        record.setCodeListCode(line[1]);
        record.setCode(line[2]);
        record.setDisplayValue(line[3]);
        setLongDescription(line, record);
        setFromDate(line, record);
        setToDate(line, record);
        setSortingPriority(line, record);
        return record;
    }

    private static void setLongDescription(String[] line, CsvRecord record) {
        record.setLongDescription(line.length > 4 && !line[4].isEmpty() ? line[4] : null);
    }

    private static void setSortingPriority(String[] line, CsvRecord record) {
        if (line.length > 7 && !line[7].isEmpty()) {
            try {
                record.setSortingPriority(Integer.parseInt(line[7]));
            } catch (NumberFormatException e) {
                log.warn("Ongeldige waarde van de sortingPriority : {}", line[7]);
                record.setSortingPriority(null);
            }
        } else {
            record.setSortingPriority(null);
        }
    }

    private static void setToDate(String[] line, CsvRecord record) {
        if (line.length > 6 && !line[6].isEmpty()) {
            try {
                record.setToDate(LocalDate.parse(line[6], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            } catch (DateTimeParseException e) {
                log.warn("Ongeldige waarde voor datum: {}", line[6]);
                record.setToDate(null);
            }
        }
    }

    private static void setFromDate(String[] line, CsvRecord record) {
        if (line.length > 5 && !line[5].isEmpty()) {
            try {
                record.setFromDate(LocalDate.parse(line[5], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            } catch (DateTimeParseException e) {
                System.out.println("Ongeldige fromDate waarde: " + line[5]);
                record.setFromDate(null);
            }
        }
    }
}
