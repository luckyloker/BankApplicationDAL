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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments;

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setCard(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
        payment.setCard(null);
    }

}
