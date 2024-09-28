package nl.top.assessment.services;

import nl.top.assessment.model.CsvRecord;
import nl.top.assessment.repositories.CsvRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CsvRecordServicesImpl implements CsvRecordServices {

    private final CsvRecordRepository repository;


    @Autowired
    public CsvRecordServicesImpl(CsvRecordRepository csvRecordRepository) {
        this.repository = csvRecordRepository;
    }

    @Override
    public List<CsvRecord> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CsvRecord> findByCode(String code) {
        return repository.findByCode(code);
    }

    public void save(CsvRecord record) {
       repository.save(record);
    }

    @Override
    public void saveAll(List<CsvRecord> csvRecords) {
        repository.saveAll(csvRecords);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }


}
