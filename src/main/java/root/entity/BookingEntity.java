package root.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking", schema = "hiking")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookingEntity {
    
    @Id @GeneratedValue 
    @Column(name = "booking_id", nullable = false, columnDefinition = "binary(16)")
    private UUID bookingId;
    
    @ManyToOne
    @JoinColumn(name = "hiker_id")
    private HikerEntity hiker;
    
    /**
     * @see root.entity.TrailEntity
     */
    @ManyToOne
    @JoinColumn(name = "trail_id")
    private TrailEntity trail;
    
    //01:to be held
    //02:event finished
    //03:event canceled
    @Column(name = "status")
    private String status;

    @Version
    @Column(name = "optimistic_lock_no")
    private Integer optimisticLockNo;
    
    @CreatedBy
    @Column(name = "creator", updatable = false, nullable = false)
    private String creator;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date_time", updatable = false, nullable = false)
    private Date creationDateTime;

    @LastModifiedBy
    @Column(name = "updater", nullable = false)
    private String updater;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updating_date_time", nullable = false)
    private Date updatingDateTime;
    
    @ManyToMany
    @JoinTable(name = "booking_hiker", schema = "hiking", joinColumns = {
            @JoinColumn(name = "booking_id") }, inverseJoinColumns = { @JoinColumn(name = "hiker_Id") })
    private Set<HikerEntity> bookedHikers;
    
    public BookingEntity(final HikerEntity hiker, final TrailEntity trail, final Set<HikerEntity> bookHikers) {
        this.hiker = hiker;
        this.trail = trail;
        this.bookedHikers = bookHikers;
    }

}
