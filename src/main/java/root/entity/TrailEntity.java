package root.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailEntity {
    @Id
    @Column(name = "trail_id", nullable = false)
    private String trailId;
    
    @Column(name = "trail_name", nullable = false)
    private String trailName;
    
    @Column(name = "start_at", nullable = false)
    private String startAt;
    
    @Column(name = "end_at", nullable = false)
    private String endAt;
    
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

}

