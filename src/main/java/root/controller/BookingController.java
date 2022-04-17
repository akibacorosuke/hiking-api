package root.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import root.dto.BookingHistoryDto;
import root.request.BookingRequest;
import root.response.BookingResponse;
import root.service.BookingService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    
    private final BookingService bookingService;
    
    /**
     * Book a trail.
     * @param bookingRequest
     * @return The response of the request.
     */
    @PostMapping("/booking")
    private ResponseEntity<UUID> createBooking(@RequestBody @NonNull final BookingRequest bookingRequest) {
        log.info(String.format("requesting a booking : %s", bookingRequest));
        final UUID bookingId = bookingService.createBooking(bookingRequest);
        // check if all required inputs are provided 
        log.info("booking is done. booking id : " + bookingId);
        return new ResponseEntity<>(bookingId, HttpStatus.OK);
    }
    
    /**
     * Get all bookings of selected hiker.
     * @param hikerId
     * @return The response of the request
     */
    @GetMapping("/bookings/hiker/{hikerId}")
    private ResponseEntity<List<BookingHistoryDto>> getBookingsByHiker(@PathVariable @NonNull final UUID hikerId) {
        log.info(String.format("requesting a view of bookings by hiker %s", hikerId));
        final List<BookingHistoryDto> dtos = bookingService.getBookingsByHiker(hikerId);
        log.info(String.format("booking history data is extracted for hiker : %s", dtos));
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    
    /**
     * Get one booking data
     * @param bookingId
     * @return BookingResponse
     */
    @GetMapping("/booking/{bookingId}")
    private ResponseEntity<BookingResponse> getBooking(@PathVariable @NonNull final UUID bookingId) {
        log.info(String.format("requesting a view of booking. bookingId : %s", bookingId));
        //getBooking
        final BookingResponse bookingResponse = bookingService.getBooking(bookingId);
        log.info(String.format("booking data is extracted. booking id : %s", bookingResponse));
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }
    
    /**
     * cancel booking by booking id
     * @param bookingID
     * @return status code
     */
    @DeleteMapping("/booking/{bookingID}")
    private ResponseEntity<?> cancelBooking(@PathVariable @NonNull final UUID bookingID) {
        log.info(String.format("requesting to cancel a booking. bookingId : %s", bookingID));
        bookingService.cancelBooking(bookingID);
        log.info(String.format("canceled a booking. bookingId : %s", bookingID));
        return ResponseEntity.noContent().build();
    }
    
}
