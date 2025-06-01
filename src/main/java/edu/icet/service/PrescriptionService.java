package edu.icet.service;

import edu.icet.dto.PrescriptionDto;

import java.util.List;

public interface PrescriptionService {
    PrescriptionDto issuPrecrition(PrescriptionDto prescription);

    List<PrescriptionDto> viewPricriptions();

    List<PrescriptionDto> searchByDate(String date);

    List<PrescriptionDto> searchByDoctor(Long doctorId);

    List<PrescriptionDto> searchByPatient(Long patientId);

    PrescriptionDto searchByAppointmentId(Long appointmentId);
}
