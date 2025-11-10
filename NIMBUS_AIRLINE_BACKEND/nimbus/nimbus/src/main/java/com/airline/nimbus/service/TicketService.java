// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/service/TicketService.java
// ============================================

package com.airline.nimbus.service;

import com.airline.nimbus.model.Booking;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class TicketService {

    public byte[] generateTicketPdf(Booking booking) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add content to PDF
            document.add(new Paragraph("AIRLINE TICKET")
                    .setBold().setFontSize(20));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Booking ID: " + booking.getId()));
            document.add(new Paragraph("Passenger: " +
                    booking.getPassenger().getFirstName() + " " +
                    booking.getPassenger().getLastName()));
            document.add(new Paragraph("Email: " +
                    booking.getPassenger().getEmail()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Flight Details:").setBold());
            document.add(new Paragraph("Flight Number: " +
                    booking.getFlight().getFlightNumber()));
            document.add(new Paragraph("From: " +
                    booking.getFlight().getSource()));
            document.add(new Paragraph("To: " +
                    booking.getFlight().getDestination()));
            document.add(new Paragraph("Date: " +
                    booking.getFlight().getDepartureDate()));
            document.add(new Paragraph("Departure Time: " +
                    booking.getFlight().getDepartureTime()));
            document.add(new Paragraph("Arrival Time: " +
                    booking.getFlight().getArrivalTime()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Seat Number: " +
                    (booking.getSeatNumber() != null ? booking.getSeatNumber() : "Will be assigned")));
            document.add(new Paragraph("Number of Seats: " +
                    booking.getNumberOfSeats()));
            document.add(new Paragraph("Total Amount: ₹" +
                    booking.getTotalAmount()));
            document.add(new Paragraph("Status: " + booking.getStatus()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Booking Date: " +
                    booking.getBookingDate()));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
