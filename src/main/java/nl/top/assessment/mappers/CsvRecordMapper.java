package nl.top.assessment.mappers;

import nl.top.assessment.dto.CsvRecordDTO;
import nl.top.assessment.model.CsvRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CsvRecordMapper {

    CsvRecord toEntity(CsvRecordDTO dto);

    CsvRecordDTO toDTO(CsvRecord entity);
}
