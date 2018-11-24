package util;

import model.Card;
import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestGenerator {

    public static User generateUser() {
        User newUser = new User();
        return newUser.builder()
                .firstName("Jon")
                .lastName("Snow")
                .passportNumber(1234456L)
                .taxNumber(987654L)
                .birthday(LocalDate.of(1989, 10, 1))
                .build();
    }

    public static Card generateCard() {
        Card newCard = new Card();
        return newCard.builder()
                .balance(BigDecimal.valueOf(100L))
                .cvv2(123)
                .expirationDate(LocalDate.of(2019,1,1))
                .build();
    }
}
