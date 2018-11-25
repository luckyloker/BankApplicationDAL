package util;

import model.Card;
import model.Payment;
import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

public class TestGenerator {

    public static User generateUser() {
        User newUser = new User();
        return newUser.builder()
                .firstName("Jon")
                .lastName("Snow")
                .passportNumber(1234456L)
                .taxNumber(987654L)
                .birthday(LocalDate.of(1989, 10, 1))
                .cards(new HashSet<>())
                .build();
    }

    public static Card generateCard() {
        Card newCard = new Card();
        return newCard.builder()
                .balance(BigDecimal.valueOf(100L))
                .cvv2(123)
                .expirationDate(LocalDate.of(2019,1,1))
                .payments(new HashSet<>())
                .build();
    }

    public static Payment generatePayment() {
        Payment newPayment = new Payment();
        return newPayment.builder()
                .amount(BigDecimal.valueOf(10L))
                .paymentTime(LocalDateTime.now())
                .build();
    }
}
