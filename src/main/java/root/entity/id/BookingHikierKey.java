package root.entity.id;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class BookingHikierKey implements Serializable {
    
    private static final long serialVersionUID = 1710512918274384705L;

    @Column(name = "hiker_id", columnDefinition = "binary(16)")
    private UUID hikerId;

    @Column(name = "booking_id", columnDefinition = "binary(16)")
    private UUID bookingId;

}
