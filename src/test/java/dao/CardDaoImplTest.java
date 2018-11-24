package dao;

import exceptions.BankAppException;
import model.Card;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.TestGenerator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CardDaoImplTest {
    static EntityManagerFactory emf;
    static CardDao cardDao;
    static UserDao userDao;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit");
        cardDao = new CardDaoImpl(emf);
        userDao = new UserDaoImpl(emf);
    }

    @AfterAll
    static void tearDown() {
        emf.close();
    }

    @Test
    public void saveCardOnExistingUser() {
        User newUser = TestGenerator.generateUser();
        userDao.saveUser(newUser);
        assertNotNull(newUser.getId());
        Card newCard = TestGenerator.generateCard();
        cardDao.saveCard(newUser, newCard);
        assertNotNull(newCard.getId());
        assertNotNull(newUser.getId());
    }

}

