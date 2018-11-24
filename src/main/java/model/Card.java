package model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "cvv_2", nullable = false)
    private int cvv2;

    @Column(name = "expiraton_date", nullable = false)
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card")
    private Set<Payment> payments = new HashSet<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setCard(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
        payment.setCard(null);
    }

}
