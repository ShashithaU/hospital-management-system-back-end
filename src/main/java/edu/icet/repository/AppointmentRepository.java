package edu.icet.repository;

import edu.icet.dto.Appointment;
import edu.icet.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Integer> {
    List<AppointmentEntity> findByAdminId(Integer id);

    List<AppointmentEntity> findByType(String type);

    List<AppointmentEntity> findByPatientId(Integer id);

    // In AppointmentRepository
    @Query("SELECT a FROM AppointmentEntity a WHERE a.dateTime >= :startOfDay AND a.dateTime < :endOfDay")
    List<AppointmentEntity> findByDate(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);


}
