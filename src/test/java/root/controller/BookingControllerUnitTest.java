package root.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import root.dto.BookingHistoryDto;
import root.request.BookingRequest;
import root.service.BookingService;

/**
 * This class is to do unit test for BookingController class.
 * @see root.controller.BookingController
 * @author daichi.akiba
 */
@Tag(value = "unit")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookingController.class)
public class BookingControllerUnitTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookingService bookingService;
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * @throws Exception 
     * @throws JsonProcessingException 
     * @see root.controller.BookingController#createBooking(BookingRequest)
     */
    @DisplayName("when Booking is successfully done, response Ok with booking id")
    @Test
    void checkCreateBooking() throws JsonProcessingException, Exception {
        final UUID bookingId = UUID.randomUUID();
        final UUID bookedByHiker = UUID.randomUUID();
        final UUID hikerIdA = UUID.randomUUID();
        final UUID hikerIdB = UUID.randomUUID();
        Mockito.when(bookingService.createBooking(Mockito.any())).thenReturn(bookingId);
        final MvcResult result = 
                mvc.perform(post("/booking")
                .content(objectMapper.writeValueAsString(new BookingRequest(bookedByHiker, new HashSet<UUID>(Arrays.asList(hikerIdA, hikerIdB)), "001")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(String.format("\"%s\"", bookingId.toString()), result.getResponse().getContentAsString());
    }
    
    /**
     * @throws Exception
     * @see root.controller.BookingController#getBookingsByHiker(hikerId)
     */
    @DisplayName("check if selected hiker's bookings are extracted")
    @Test
    void checkGetHikerBookings() throws Exception {
        final UUID hikerId = UUID.randomUUID();
        final String bookedDateA = "Thursday June 15 2022";
        final String bookedDateB = "Saturday June 17 2022";
        final UUID bookingIdA = UUID.randomUUID();
        final UUID bookingIdB = UUID.randomUUID();
        final List<BookingHistoryDto> dtos = new ArrayList<>();
        dtos.add(new BookingHistoryDto(bookingIdA, "mountain", "07:00", "11:00", bookedDateA, "01"));
        dtos.add(new BookingHistoryDto(bookingIdB, "sea", "09:00", "12:00", bookedDateB, "02"));
        Mockito.when(bookingService.getBookingsByHiker(Mockito.any())).thenReturn(dtos);
        
        final String expectedBody = 
                "[{\"bookingId\":\"" + bookingIdA
                + "\",\"trailId\":\"mountain\","
                + "\"startAt\":\"07:00\",\"endAt\":\"11:00\","
                + "\"bookedTime\":\"" + bookedDateA
                + "\",\"status\":\"01\"},"
                + "{\"bookingId\":\"" + bookingIdB
                + "\",\"trailId\":\"sea\","
                + "\"startAt\":\"09:00\",\"endAt\":\"12:00\","
                + "\"bookedTime\":\"" + bookedDateB
                + "\",\"status\":\"02\"}]";
        
        final MvcResult result = 
                mvc.perform(get("/bookings/hiker/" + hikerId))
                .andExpect(status().isOk()).andReturn();    
        Assertions.assertEquals(expectedBody, result.getResponse().getContentAsString());
    }
    
}
