package util;

import model.Card;
import model.Payment;
import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestGenerator {

    public static User generateUser() {
        String firstNames[] = {"Joh", "Daynerys", "Tyrion", "Jaime", "Hodor"};
        String lastNames[] = {"Stark", "Targaryen", "Lannister", "Tyrell", "Tully"};
        User newUser = new User();
        return newUser.builder()
                .firstName(firstNames[(int) (Math.random() * 5)])
                .lastName(lastNames[(int) (Math.random() * 5)])
                .passportNumber(ThreadLocalRandom.current().nextLong(100, 10000))
                .taxNumber(ThreadLocalRandom.current().nextLong(1000, 9999))
                .birthday(randomLocalDate())
                .build();
    }

    private static LocalDate randomLocalDate() {
        int year = ThreadLocalRandom.current().nextInt(1971, 2010);
        int month = ThreadLocalRandom.current().nextInt(1,12);
        int day = ThreadLocalRandom.current().nextInt(1, 30);
        return LocalDate.of(year, month, day);
    }


    public static Card generateCard() {
        Card newCard = new Card();
        return newCard.builder()
                .balance(BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 1000)))
                .cvv2(ThreadLocalRandom.current().nextInt(100, 999))
                .expirationDate(randomLocalDate())
                .build();
    }

    public static Payment generatePayment() {
        Payment newPayment = new Payment();
        return newPayment.builder()
                .amount(BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(0, 1000)))
                .paymentTime(LocalDateTime.now())
                .build();
    }
}
