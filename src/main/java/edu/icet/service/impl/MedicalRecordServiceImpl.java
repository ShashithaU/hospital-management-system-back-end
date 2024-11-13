package edu.icet.service.impl;

import edu.icet.dto.MedicalReport;
import edu.icet.entity.MedicalReportEntity;
import edu.icet.repository.MedicalReportRepository;
import edu.icet.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    final ModelMapper mapper;
    final MedicalReportRepository repository;
    @Override
    public void addMedicalReport(MedicalReport medicalReport) {
        repository.save(mapper.map(medicalReport, MedicalReportEntity.class));
    }
    @Override
    public List<MedicalReport> getMedicalRecords() {
        List<MedicalReport> medicalReportList = new ArrayList<>();
        repository.findAll().forEach(entity->{
            medicalReportList.add(mapper.map(entity,MedicalReport.class));
        });
        return medicalReportList;
    }

    @Override
    public Boolean deleteRecordById(Integer id) {
        repository.deleteById(id);
        return true;
    }
}
