package edu.icet.service;

import edu.icet.dto.MedicalReport;

import java.util.List;

public interface MedicalRecordService {
    void addMedicalReport(MedicalReport medicalReport);

    List<MedicalReport> getMedicalRecords();

    Boolean deleteRecordById(Integer id);
}
