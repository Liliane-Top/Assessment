package nl.top.assessment.repositories;

import nl.top.assessment.model.CsvRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CsvRecordRepository extends JpaRepository<CsvRecord, Long> {
    Optional<CsvRecord> findByCode(String code);
    boolean existsByCode(String code);
}
