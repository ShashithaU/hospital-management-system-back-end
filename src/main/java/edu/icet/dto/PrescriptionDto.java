package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
    private Long id;
    private String type;
    private LocalDateTime dateTime;
    private Long patientId;
    private Long adminId;
    private String description;
    private String patientStatus;
    private String status;
}
