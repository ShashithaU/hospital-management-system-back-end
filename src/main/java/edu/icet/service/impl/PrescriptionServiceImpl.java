package edu.icet.service.impl;

import com.itextpdf.text.pdf.qrcode.Mode;
import edu.icet.dto.PrescriptionDto;
import edu.icet.entity.PrescriptionEntity;
import edu.icet.repository.PrescriptionRepository;
import edu.icet.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final ModelMapper modelMapper;
    private final PrescriptionRepository prescriptionRepository;

    @Override
    public PrescriptionDto issuPrecrition(PrescriptionDto prescription) {
        PrescriptionEntity entity = modelMapper.map(prescription, PrescriptionEntity.class);
        entity = prescriptionRepository.save(entity);
        return modelMapper.map(entity, PrescriptionDto.class);
    }

    @Override
    public List<PrescriptionDto> viewPricriptions() {
        return prescriptionRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, PrescriptionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriptionDto> searchByDate(String date) {
        LocalDate searchDate = LocalDate.parse(date);
        LocalDateTime startOfDay = searchDate.atStartOfDay();
        LocalDateTime endOfDay = searchDate.plusDays(1).atStartOfDay().minusNanos(1);

        return prescriptionRepository.findByDateTimeBetween(startOfDay, endOfDay).stream()
                .map(entity -> modelMapper.map(entity, PrescriptionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriptionDto> searchByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId).stream()
                .map(entity -> modelMapper.map(entity, PrescriptionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriptionDto> searchByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId).stream()
                .map(entity -> modelMapper.map(entity, PrescriptionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PrescriptionDto searchByAppointmentId(Long appointmentId) {
        return prescriptionRepository.findById(appointmentId)
                .map(entity -> modelMapper.map(entity, PrescriptionDto.class))
                .orElse(null);
    }
}