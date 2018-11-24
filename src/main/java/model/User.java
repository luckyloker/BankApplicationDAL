package model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "passport_number", nullable = false)
    private Long passportNumber;

    @Column(name = "tax_number", nullable = false)
    private Long taxNumber;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>(10);

    public void addCard(Card card) {
        card.setUser(this);
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.setUser(null);
    }
}
