package root.response;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingResponse {
    
    private UUID bookingId;
    
    private UUID bookedByHiker;
    
    private String trailCode;
    
    private Set<UUID> bookedHikers;
    
    private Date bookedTime;

}