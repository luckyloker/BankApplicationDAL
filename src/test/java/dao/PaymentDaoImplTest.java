package dao;

import model.Card;
import model.Payment;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestGenerator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static util.PersistenceContextOperations.*;

class PaymentDaoImplTest {
    static EntityManagerFactory emf;
    private CardDao cardDao;
    private UserDao userDao;
    private PaymentDao paymentDao;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit");
        cardDao = new CardDaoImpl(emf);
        userDao = new UserDaoImpl(emf);
        paymentDao = new PaymentDaoImpl(emf);

    }

    @AfterEach
    void tearDown() {
        emf.close();
    }


    @Test
    void savePayment() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        Payment newPayment = TestGenerator.generatePayment();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        paymentDao.savePayment(newCard, newPayment);
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            Card paymentCard = entityManager.find(Card.class, newCard.getId());
            List<Payment> paymentSet = paymentCard.getPayments();
            System.out.println(paymentSet);
        });
        assertNotNull(newPayment.getId());
    }

    @Test
    void findPaymentById() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        Payment newPayment = TestGenerator.generatePayment();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        paymentDao.savePayment(newCard, newPayment);
        Payment findedPayment = paymentDao.findPaymentById(newPayment.getId());
        assertThat(newPayment.getAmount().intValue(), is(findedPayment.getAmount().intValue()));
    }

    @Test
    void removePayment() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        Payment newPayment = TestGenerator.generatePayment();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        paymentDao.savePayment(newCard, newPayment);
        Payment anotherPayment = TestGenerator.generatePayment();
        paymentDao.savePayment(newCard, anotherPayment);
        paymentDao.removePayment(newPayment);
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            Card paymentCard = entityManager.find(Card.class, newCard.getId());
            List<Payment> paymentSet = paymentCard.getPayments();
            System.out.println(paymentSet);
            assertThat(paymentSet.size(), is(1));
        });
    }

    @Test
    void updatePayment() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        Payment newPayment = TestGenerator.generatePayment();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        paymentDao.savePayment(newCard, newPayment);
        newPayment.setAmount(BigDecimal.valueOf(17L));
        paymentDao.updatePayment(newPayment);
        assertThat(paymentDao.findPaymentById(newPayment.getId()).getAmount().longValue(), is(17L));
    }

    @Test
    void findAllPaymentsByCard() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        List<Payment> paymentList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            paymentList.add(TestGenerator.generatePayment());
            paymentDao.savePayment(newCard, paymentList.get(i));
        }
        List<Payment> paymentListFromDb = paymentDao.findAllByCard(newCard);
        System.out.println(paymentList);
        System.out.println(paymentListFromDb);
        assertThat(paymentList, containsInAnyOrder(paymentListFromDb));
    }

    @Test
    void findAllPaymentsByUser() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        List<Payment> paymentList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            paymentList.add(TestGenerator.generatePayment());
            paymentDao.savePayment(newCard, paymentList.get(i));
        }
        List<Payment> paymentListFromDb = paymentDao.findAllByUser(newUser);
        assertThat(paymentList, containsInAnyOrder(paymentListFromDb));
    }

    @Test
    void findAllPaymnetsByUserAndDate() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        List<Payment> paymentList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            paymentList.add(TestGenerator.generatePayment());
            paymentDao.savePayment(newCard, paymentList.get(i));
        }
        List<Payment> paymentListFromDb = paymentDao.findAllByUserAndDate(newUser.getId(), LocalDate.of(1980, 1,1));
        System.out.println(paymentList);
        System.out.println(paymentListFromDb);
    }

    @Test
    void findAllPaymentsByUserAndAmount() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        List<Payment> paymentList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            paymentList.add(TestGenerator.generatePayment());
            paymentDao.savePayment(newCard, paymentList.get(i));
        }
        List<Payment> paymentListFromDb = paymentDao.findAllAmountMoreThan(newUser.getId(), BigDecimal.valueOf(100L));
        System.out.println(paymentList);
        System.out.println(paymentListFromDb);
        assertThat(paymentList, containsInAnyOrder(paymentListFromDb));
    }
}