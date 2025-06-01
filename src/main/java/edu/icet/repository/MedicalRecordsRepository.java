package edu.icet.repository;

import edu.icet.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecordEntity,Long> {

    List<MedicalRecordEntity> findByPatientId(Long patientId);

    List<MedicalRecordEntity> findByCategory(String category);
}
