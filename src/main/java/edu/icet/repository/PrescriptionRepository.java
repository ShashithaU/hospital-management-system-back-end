package edu.icet.repository;

import edu.icet.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity,Long> {

    List<PrescriptionEntity> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<PrescriptionEntity> findByDoctorId(Long doctorId);
    List<PrescriptionEntity> findByPatientId(Long patientId);
    Optional<PrescriptionEntity> findById(Long appointmentId);
}
