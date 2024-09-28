package nl.top.assessment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
public class CsvRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private String source;
   private String codeListCode;

   @Column(unique = true)
   private String code;

   private String displayValue;
   private String longDescription;

   private LocalDate fromDate;
   private LocalDate toDate;

   private Integer sortingPriority;
}
