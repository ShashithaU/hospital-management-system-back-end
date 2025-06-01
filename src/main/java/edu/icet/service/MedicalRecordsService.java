package edu.icet.service;

import com.lowagie.text.DocumentException;
import edu.icet.dto.MedicalRecordDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicalRecordsService {
    void addMedicalRecord(MedicalRecordDto medicalRecordDto);

    List<MedicalRecordDto> getMedicalRecordsByPatientId(Long patientId);

    ResponseEntity<byte[]> downloadMedicalRecordPdf(Long recordId);

    void deleteMedicalRecord(Long recordId);

    List<MedicalRecordDto> searchByCategory(String category);

    ResponseEntity<byte[]> printMedicalRecord(Long recordId) throws DocumentException;
}
