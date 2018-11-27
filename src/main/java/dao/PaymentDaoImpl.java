package dao;

import model.Card;
import model.Payment;
import model.User;
import util.PersistenceContextOperations;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static util.PersistenceContextOperations.*;

public class PaymentDaoImpl implements PaymentDao {
    private PersistenceContextOperations persistenceUtil;


    public PaymentDaoImpl(EntityManagerFactory emf) {
        this.persistenceUtil = new PersistenceContextOperations(emf);
    }

    @Override
    public void savePayment(Card card, Payment payment) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager -> {
            Card paymentCard = entityManager.merge(card);
            paymentCard.addPayment(payment);
        });
    }

    @Override
    public void removePayment(Payment payment) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager -> {
            Payment removedPayment = entityManager.find(Payment.class, payment.getId());
            Card cardToRemovePayment = entityManager.find(Card.class, removedPayment.getCard().getId());
            cardToRemovePayment.removePayment(payment);
//           entityManager.remove(payment);
        });
    }

    @Override
    public Payment findPaymentById(Long id) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.find(Payment.class, id));
    }

    @Override
    public void updatePayment(Payment payment) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager ->
                entityManager.merge(payment));
    }

    @Override
    public List<Payment> findAllByCard(Card cardId) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select p from Payment p where p.card.id = :id", Payment.class)
                        .setParameter("id", cardId.getId())
                        .getResultList());
//           Card paymentCard = entityManager.find(Card.class, cardId.getId());
//           return paymentCard.getPayments();
    }

    @Override
    public List<Payment> findAllByUser(User userId) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select p from Payment p where p.card.user.id = :id", Payment.class)
                        .setParameter("id", userId.getId())
                        .getResultList());
    }

    @Override
    public List<Payment> findAllByUserAndDate(Long userId, LocalDate date) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select p from Payment p where p.card.user.id = :id and " +
                        "p.paymentTime >= :startTime and p.paymentTime <= :endTime", Payment.class)
                        .setParameter("id", userId)
                        .setParameter("startTime", LocalDateTime.of(date, LocalTime.of(0,0)))
                        .setParameter("endTime", LocalDateTime.of(date, LocalTime.of(23,59)))
                        .getResultList());
    }

    @Override
    public List<Payment> findAllAmountMoreThan(Long userId, BigDecimal amount) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select p from Payment p where p.card.user.id = :id and " +
                        "p.amount >= : amountValue", Payment.class)
                        .setParameter("id", userId)
                        .setParameter("amountValue", amount)
                        .getResultList());
    }
}
