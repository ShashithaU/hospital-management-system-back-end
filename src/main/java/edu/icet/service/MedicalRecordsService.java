package edu.icet.service;

import edu.icet.dto.MedicalRecordDto;
import edu.icet.dto.Patient;

import java.util.List;

public interface MedicalRecordsService {
    void addMedicalRecord(MedicalRecordDto medicalRecordDto);

    List<MedicalRecordDto> getMedicalRecordsByPatientId(Long patientId);

    void downloadMedicalRecordPdf(Long recordId);

    void deleteMedicalRecord(Long recordId);

    List<MedicalRecordDto> searchByCategory(String category);

    boolean printMedicalRecord(Long recordId);

    void addPatient(Patient patient);
}
