package edu.icet.service;

import edu.icet.dto.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment addAppointment(Appointment appointment);

    Boolean deleteById(Integer id);

    List<Appointment> getAll();

    Appointment getAppointmentById(Integer id);

    List<Appointment> getAppointmentByAdminId(Integer id);

    List<Appointment> getAppointmentByType(String type);

    List<Appointment> getAppointmentByPatientId(Integer id);

    List<Appointment> getAppointmentByDate(String date);
}
