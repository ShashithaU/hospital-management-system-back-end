package edu.icet.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.icet.dto.Appointment;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;



@Service
public class PdfGeneratorService {
    public byte[] generateAppointmentPdf(Appointment appointment) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph title = new Paragraph("Appointment Confirmation", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add hospital logo (if you have one)
        // Image logo = Image.getInstance("path/to/logo.png");
        // logo.scaleToFit(100, 100);
        // logo.setAlignment(Element.ALIGN_CENTER);
        // document.add(logo);

        // Add some space
        document.add(new Paragraph(" "));

        // Add appointment details
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("Dear " + appointment.getEmail() + ",", contentFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Your appointment has been scheduled successfully:", contentFont));
        document.add(new Paragraph(" "));

        // Create a table for better formatting
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        addTableRow(table, "Doctor:", appointment.getEmail(), contentFont);
        addTableRow(table, "Date:", appointment.getEmail().toString(), contentFont);
        addTableRow(table, "Time:", appointment.getEmail().toString(), contentFont);
        addTableRow(table, "Department:", appointment.getEmail(), contentFont);
        addTableRow(table, "Location:", "Main Hospital Building, Room 205", contentFont);
        addTableRow(table, "Reference ID:", appointment.getEmail(), contentFont);

        document.add(table);
        document.add(new Paragraph(" "));

        // Add footer
        Paragraph footer = new Paragraph("Thank you for choosing Our Hospital. Please arrive 15 minutes before your scheduled time.", contentFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return outputStream.toByteArray();
    }

    private void addTableRow(PdfPTable table, String label, String value, Font font) {
        table.addCell(createCell(label, font, true));
        table.addCell(createCell(value, font, false));
    }

    private PdfPCell createCell(String content, Font font, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(PdfPCell.NO_BORDER);
        if (isHeader) {
            cell.setBackgroundColor(new BaseColor(240, 240, 240));
        }
        return cell;
    }
}