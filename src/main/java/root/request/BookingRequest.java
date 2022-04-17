package root.request;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    
    private UUID bookedByHiker;
    
    private Set<UUID> hikers;
    
    private String trailId;
}
