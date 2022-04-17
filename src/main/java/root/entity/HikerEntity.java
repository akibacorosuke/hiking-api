package root.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "hiker")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HikerEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiker_id", nullable = false, columnDefinition = "binary(16)")
    private UUID hikerId;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "age", nullable = false)
    private int age;
    
    @NonNull
    @CreatedBy
    @Column(name = "creator", updatable = false)
    private String creator;

    @NonNull
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date_time", updatable = false)
    private Date creationDateTime;

    @LastModifiedBy
    @Column(name = "updater", nullable = false)
    private String updater;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updating_date_time", nullable = false)
    private Date updatingDateTime;
    
    public HikerEntity(final UUID hikerId, final String firstName, final String lastName, final int age,
            final String updater, final Date updatingDateTime) {
        this.hikerId = hikerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.updater = updater;
        this.updatingDateTime = updatingDateTime;
    }

}
