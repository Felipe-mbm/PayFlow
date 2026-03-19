package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transactional {

    private String id;
    private BigDecimal amount;

    @CurrentTimestamp
    private OffsetDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
