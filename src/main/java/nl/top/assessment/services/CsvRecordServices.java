package nl.top.assessment.services;

import nl.top.assessment.model.CsvRecord;

import java.util.List;
import java.util.Optional;

public interface CsvRecordServices {

    List<CsvRecord> findAll();

    Optional<CsvRecord> findByCode(String code);

    void save(CsvRecord record);

    void saveAll(List<CsvRecord> csvRecords);

    void deleteAll();

}
