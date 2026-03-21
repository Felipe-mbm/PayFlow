package entity;

import enums.Status;
import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "amount")
    private Integer amount;

    @CurrentTimestamp
    private OffsetDateTime dateTime;

    @Column(name = "status")
    private Status status;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "pix_key", length = 500)
    private String pixKey;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
