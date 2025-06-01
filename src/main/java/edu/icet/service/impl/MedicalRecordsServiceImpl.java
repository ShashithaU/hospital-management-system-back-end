package edu.icet.service.impl;

import edu.icet.dto.MedicalRecordDto;
import edu.icet.dto.Patient;
import edu.icet.entity.MedicalRecordEntity;
import edu.icet.repository.MedicalRecordsRepository;
import edu.icet.service.MedicalRecordsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    private final MedicalRecordsRepository medicalRecordsRepository;
    private final ModelMapper modelMapper;


    @Override
    public void addMedicalRecord(MedicalRecordDto medicalRecordDto) {
        MedicalRecordEntity entity = modelMapper.map(medicalRecordDto, MedicalRecordEntity.class);
        medicalRecordsRepository.save(entity);
    }

    @Override
    public List<MedicalRecordDto> getMedicalRecordsByPatientId(Long patientId) {
        List<MedicalRecordEntity> entities = medicalRecordsRepository.findByPatientId(patientId);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, MedicalRecordDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void downloadMedicalRecordPdf(Long recordId) {
//TODO
    }

    @Override
    public void deleteMedicalRecord(Long recordId) {
        medicalRecordsRepository.deleteById(recordId);

    }

    @Override
    public List<MedicalRecordDto> searchByCategory(String category) {
        List<MedicalRecordEntity> entities = medicalRecordsRepository.findByCategory(category);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, MedicalRecordDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean printMedicalRecord(Long recordId) {
        Optional<MedicalRecordEntity> record = medicalRecordsRepository.findById(recordId);
        //TODO Implement print logic here
        return record.isPresent();
    }

    @Override
    public void addPatient(Patient patient) {
//TODO  // Not implemented here; typically handled by PatientService
    }
}
