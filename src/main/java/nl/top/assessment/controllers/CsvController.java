package nl.top.assessment.controllers;

import nl.top.assessment.dto.CsvRecordDTO;
import nl.top.assessment.mappers.CsvRecordMapper;
import nl.top.assessment.model.CsvRecord;
import nl.top.assessment.services.CsvRecordServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CsvController {

    private final CsvRecordServices service;
    private final CsvRecordMapper csvRecordMapper;

    @Autowired
    public CsvController(CsvRecordServices csvRecordServices, CsvRecordMapper csvRecordMapper) {
        this.service = csvRecordServices;
        this.csvRecordMapper = csvRecordMapper;
    }

    @GetMapping("/{code}")
    public ResponseEntity<CsvRecord> getByCode(@PathVariable String code) {
        return service.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<CsvRecord> getAllRecords() {
        return service.findAll();
    }

    @PostMapping("/records")
    public ResponseEntity<Void> createRecord(@RequestBody CsvRecordDTO csvRecordDTO) {
        CsvRecord record = csvRecordMapper.toEntity(csvRecordDTO);
        service.save(record);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAllRecords() {
        service.deleteAll();
        return ResponseEntity.ok("All records deleted!");
    }
}
