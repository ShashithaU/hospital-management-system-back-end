package edu.icet.repository;

import edu.icet.entity.MedicalReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MedicalReportRepository extends JpaRepository<MedicalReportEntity,Integer> {
}
