package edu.icet.service.impl;

import edu.icet.dto.Appointment;
import edu.icet.entity.AppointmentEntity;
import edu.icet.repository.AppointmentRepository;
import edu.icet.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    final AppointmentRepository repository;
    final ModelMapper mapper;
    final PdfGeneratorService pdfGeneratorService;
    final EmailService emailService;
    final CacheManager cacheManager;

    @Override
    @CachePut(value = "APPOINTMENT_CACHE", key = "#appointment.id") //cache name  result is built in variable
    public void addAppointment(Appointment appointment) {
        log.info(appointment.toString());
        AppointmentEntity savedAppointment = repository.save(mapper.map(appointment, AppointmentEntity.class));

        // Send confirmation email
        emailService.sendAppointmentConfirmation(mapper.map(savedAppointment,Appointment.class));
    }

    @Override
    @CacheEvict(value = "APPOINTMENT_CACHE", key = "#id")    public Boolean deleteById(Integer id) {
        repository.deleteById(id);
        return true;

    }

    @Override
    @Cacheable(value = "APPOINTMENT_CACHE", key = "'all'") // if cannot find the result in cache the below commad is execute ( get data through repository)
    public List<Appointment> getAll() {
        List<Appointment> appointmentList = new ArrayList<>();
        repository.findAll().forEach(entity -> appointmentList.add(mapper.map(entity, Appointment.class)));
        return appointmentList;
    }

    @Override
    @Cacheable(value = "APPOINTMENT_CACHE", key = "#id")
    public Appointment getAppointmentById(Integer id) {
        return mapper.map(repository.findById(id), Appointment.class);
    }

    @Override
    @Cacheable(value = "APPOINTMENT_CACHE", key = "'adminId_' + #id")
    public List<Appointment> getAppointmentByAdminId(Integer id) {
        List<Appointment> appointmentList = new ArrayList<>();
        repository.findByAdminId(id).forEach(entity -> {
            appointmentList.add(mapper.map(entity, Appointment.class));
        });
        return appointmentList;
    }

    @Override
    @Cacheable(value = "APPOINTMENT_CACHE", key = "'type_' + #type")
    public List<Appointment> getAppointmentByType(String type) {
        List<Appointment> appointmentList = new ArrayList<>();
        repository.findByType(type).forEach(entity -> {
            appointmentList.add(mapper.map(entity, Appointment.class));
        });
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppointmentByPatientId(Integer id) {
        List<Appointment> appointmentList = new ArrayList<>();
        repository.findByPatientId(id).forEach(entity -> {
            appointmentList.add(mapper.map(entity, Appointment.class));
        });
        return appointmentList;
    }

    @Override
    public List<Appointment> getAppointmentByDate(String date) {
        LocalDate selectedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = selectedDate.atStartOfDay();
        LocalDateTime endOfDay = selectedDate.plusDays(1).atStartOfDay();

        List<Appointment> appointmentList = new ArrayList<>();
        repository.findByDate(startOfDay, endOfDay).forEach(entity -> {
            appointmentList.add(mapper.map(entity, Appointment.class));
        });
        return appointmentList;
    }

    // Add this method to the service
    @CacheEvict(value = "APPOINTMENT_CACHE", allEntries = true)
    public void clearAppointmentCache() {
        // This method can be called when you need to clear all appointments cache
    }

}