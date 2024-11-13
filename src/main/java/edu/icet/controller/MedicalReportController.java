package edu.icet.controller;

import edu.icet.dto.MedicalReport;
import edu.icet.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/medical-report")
@RequiredArgsConstructor
public class MedicalReportController {

    final MedicalRecordService service;
    @PostMapping("/add-report")
    public void addMedicalReport(@RequestBody MedicalReport medicalReport){
        service.addMedicalReport(medicalReport);
    }
    @GetMapping("/get-reports")
    public List<MedicalReport> getMedicalReports(){
       return service.getMedicalRecords();
    }
    @DeleteMapping("/delete-report/{id}")
    public Boolean deleteReport(@PathVariable Integer id){
       return service.deleteRecordById(id);
    }
}
