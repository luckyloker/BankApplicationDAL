package model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;
    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id")
    private Card card;
}
