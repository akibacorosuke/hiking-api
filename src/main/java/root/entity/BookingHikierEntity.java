package root.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import root.entity.id.BookingHikierKey;

@Entity
@Table(name = "booking_hiker", schema = "hiking")
public class BookingHikierEntity implements Serializable {
    
    private static final long serialVersionUID = 5940554191160425082L;

    @EmbeddedId
    private BookingHikierKey id;
    
    //01:to go
    //02:canceled
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default '01'")
    private String status;

}
