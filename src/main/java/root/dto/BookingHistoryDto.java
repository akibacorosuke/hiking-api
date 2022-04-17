package root.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingHistoryDto {
    
    private UUID bookingId;
    
    private String trailId;
    
    private String startAt;
    
    private String endAt;
    
    private String bookedTime;
    
    private String status;

}
