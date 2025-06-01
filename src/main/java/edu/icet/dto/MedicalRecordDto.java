package edu.icet.dto;

import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDto {

    private String labNo;
    private Long adminId;
    private Long patientId;
    private LocalDateTime dateTime;
    private String pdfSrc;
    private String category;
    private Long id;

}
