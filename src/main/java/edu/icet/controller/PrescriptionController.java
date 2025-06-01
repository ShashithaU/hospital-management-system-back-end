package edu.icet.controller;

import edu.icet.dto.PrescriptionDto;
import edu.icet.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping("issue-precription")
    @ResponseStatus(HttpStatus.OK)
    public PrescriptionDto issuPrecrition(PrescriptionDto prescription){
      return prescriptionService.issuPrecrition(prescription);
    }

    @GetMapping("/view-precriptions")
    @ResponseStatus(HttpStatus.OK)
    public List<PrescriptionDto> viewPricriptions(){
        return prescriptionService.viewPricriptions();
    }

    @GetMapping("/search-by-date")
    @ResponseStatus(HttpStatus.OK)
    public List<PrescriptionDto> searchByDate(String date) {
        return prescriptionService.searchByDate(date);
    }

    @GetMapping("/search-by-doctor")
    @ResponseStatus(HttpStatus.OK)
    public List<PrescriptionDto> searchByDoctor(Long doctorId) {
        return prescriptionService.searchByDoctor(doctorId);
    }

    @GetMapping("/search-by-patient")
    @ResponseStatus(HttpStatus.OK)
    public List<PrescriptionDto> searchByPatient(Long patientId) {
        return prescriptionService.searchByPatient(patientId);
    }

    @GetMapping("/search-by-appointment")
    @ResponseStatus(HttpStatus.OK)
    public PrescriptionDto searchByAppointmentId(Long appointmentId) {
        return prescriptionService.searchByAppointmentId(appointmentId);
    }



}
