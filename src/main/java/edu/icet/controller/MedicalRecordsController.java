package edu.icet.controller;

import com.lowagie.text.DocumentException;
import edu.icet.dto.MedicalRecordDto;
import edu.icet.service.MedicalRecordsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicalRecords")
public class MedicalRecordsController {

    private final ModelMapper modelMapper;
    private final MedicalRecordsService medicalRecordsService;

    // Add Medical Record
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMedicalRecord(@RequestBody MedicalRecordDto medicalRecordDto) {
        medicalRecordsService.addMedicalRecord(medicalRecordDto);
    }

    // View Medical Records by Patient ID
    @GetMapping("/view/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MedicalRecordDto> viewMedicalRecords(@PathVariable Long patientId) {
        return medicalRecordsService.getMedicalRecordsByPatientId(patientId);
    }

    // Download Medical Record PDF
    @GetMapping("/download/{recordId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity downloadMedicalRecord(@PathVariable Long recordId) {
        medicalRecordsService.downloadMedicalRecordPdf(recordId);
        return null;

    }

    // Delete Medical Record by ID
    @DeleteMapping("/delete/{recordId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMedicalRecord(@PathVariable Long recordId) {
        medicalRecordsService.deleteMedicalRecord(recordId);
    }

    // Search Medical Records by Category
    @GetMapping("/search-by-category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<MedicalRecordDto> searchMedicalRecordsByCategory(@PathVariable String category) {
        return medicalRecordsService.searchByCategory(category);
    }

    // Print Medical Records (stub - implement PDF/print logic as needed)
    @GetMapping("/print/{recordId}")
    public ResponseEntity<byte[]> printMedicalRecord(@PathVariable Long recordId) {
        try {
            return medicalRecordsService.printMedicalRecord(recordId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    // Search Medical Records by Patient (by patientId)
    @GetMapping("/search-by-patient/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MedicalRecordDto> searchMedicalRecordsByPatient(@PathVariable Long patientId) {
        return medicalRecordsService.getMedicalRecordsByPatientId(patientId);
    }

}


