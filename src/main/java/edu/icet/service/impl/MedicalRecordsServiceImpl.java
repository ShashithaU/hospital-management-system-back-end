package edu.icet.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import edu.icet.dto.MedicalRecordDto;
import edu.icet.entity.MedicalRecordEntity;
import edu.icet.repository.MedicalRecordsRepository;
import edu.icet.service.MedicalRecordsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    private final MedicalRecordsRepository medicalRecordsRepository;
    private final ModelMapper modelMapper;
    private final PdfGeneratorService pdfGeneratorService;

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
    public ResponseEntity<byte[]> downloadMedicalRecordPdf(Long recordId) {
        Optional<MedicalRecordEntity> recordOpt = medicalRecordsRepository.findById(recordId);
        if (recordOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MedicalRecordEntity record = recordOpt.get();
        Path pdfPath = Paths.get(record.getPdfSrc());
        byte[] pdfBytes;
        try {
            pdfBytes = Files.readAllBytes(pdfPath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("medical_record_" + recordId + ".pdf").build());
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
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
    public ResponseEntity<byte[]> printMedicalRecord(Long recordId) {
        Optional<MedicalRecordEntity> recordOpt = medicalRecordsRepository.findById(recordId);
        if (recordOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MedicalRecordEntity record = recordOpt.get();
        MedicalRecordDto recordDto = modelMapper.map(record, MedicalRecordDto.class);

        byte[] pdfBytes;
        try {
            // Assuming generateMedicalRecordPdf method exists in PdfGeneratorService
            // and throws com.itextpdf.text.DocumentException
            pdfBytes = pdfGeneratorService.generateMedicalRecordPdf(recordDto);
        } catch (com.itextpdf.text.DocumentException e) {
            // Log the exception e
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error generating PDF: " + e.getMessage()).getBytes());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Set to inline to display in browser, or attachment to download
        headers.setContentDisposition(ContentDisposition.inline().filename("medical_record_" + recordId + ".pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

}

