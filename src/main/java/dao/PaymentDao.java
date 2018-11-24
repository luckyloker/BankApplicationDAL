package dao;

import model.Card;
import model.Payment;
import model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaymentDao {

    void savePayment(Payment payment);

    void removePayment(Payment payment);

    Payment findPaymentById(Long id);

    void updatePayment(Payment payment);

    List<Payment> findAllByCard(Card cardId);

    List<Payment> findAllByUser(User userId);

    List<Payment> findAllByUserAndDate(Long userId, LocalDate date);

    List<Payment> findAllAmountMoreThan(Long userId, BigDecimal amount);
}
