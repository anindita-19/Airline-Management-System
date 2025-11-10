// ============================================
// FILE LOCATION: airline-backend/src/main/java/com/airline/controller/TicketController.java
// UPDATED with better logging
// ============================================

package com.airline.nimbus.controller;

import com.airline.nimbus.model.Booking;
import com.airline.nimbus.service.BookingService;
import com.airline.nimbus.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = {"http://localhost:5500", "http://localhost:5501", "http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{bookingId}/download")
    public ResponseEntity<byte[]> downloadTicket(@PathVariable Long bookingId) {
        System.out.println("=== DOWNLOAD TICKET REQUEST ===");
        System.out.println("Booking ID: " + bookingId);

        Booking booking = bookingService.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("❌ Booking not found");
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = ticketService.generateTicketPdf(booking);

        if (pdfBytes == null) {
            System.out.println("❌ Failed to generate PDF");
            return ResponseEntity.internalServerError().build();
        }

        System.out.println("✅ Ticket PDF generated successfully (" + pdfBytes.length + " bytes)");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ticket_" + bookingId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}