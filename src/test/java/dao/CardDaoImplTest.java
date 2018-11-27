package dao;

import exceptions.BankAppException;
import model.Card;
import model.User;
import org.junit.jupiter.api.*;
import util.TestGenerator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static util.PersistenceContextOperations.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CardDaoImplTest {
    static EntityManagerFactory emf;
    private CardDao cardDao;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit");
        cardDao = new CardDaoImpl(emf);
        userDao = new UserDaoImpl(emf);
    }

    @AfterEach
    void tearDown() {
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

    @Test
    public void saveCardOnNonExistingUser() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        assertThrows(BankAppException.class, () -> cardDao.saveCard(newUser, newCard));
    }


    @Test
    public void findCardById() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        Card cardFromDb = cardDao.findCardById(newCard.getId());
        assertThat(newCard.getBalance().intValue(), is(cardFromDb.getBalance().intValue()));
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            List<User> cardsList = entityManager.createQuery("select u from User u join fetch u.cards where u.id = :id", User.class)
                    .setParameter("id", newUser.getId())
                    .getResultList();
            System.out.println(cardsList);
            assertThat(cardsList.size(), is(1));
        });
    }

    @Test
    public void removeCard() {
        User newUser = TestGenerator.generateUser();
        Card newCard = TestGenerator.generateCard();
        userDao.saveUser(newUser);
        cardDao.saveCard(newUser, newCard);
        assertNotNull(newCard.getId());
        cardDao.removeCard(newCard);
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            List<User> cardsList = entityManager.createQuery("select u from User u join fetch u.cards where u.id = :id", User.class)
                    .setParameter("id", newUser.getId())
                    .getResultList();
            System.out.println(cardsList);
            assertThat(cardsList.size(), is(0));
        });
    }

    @Test
    public void getCardList() {
        User newUser = TestGenerator.generateUser();
        userDao.saveUser(newUser);
        List<Card> cardListToAdd = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            cardListToAdd.add(TestGenerator.generateCard());
            cardDao.saveCard(newUser, cardListToAdd.get(i));
        }
        assertThat(cardListToAdd.get(0).getBalance().intValue(), is(cardDao.findCardById(1L).getBalance().intValue()));
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            List<User> cardsList = entityManager.createQuery("select u from User u join fetch u.cards where u.id = :id", User.class)
                    .setParameter("id", newUser.getId())
                    .getResultList();
            System.out.println(cardsList);
            assertThat(cardsList.size(), is(3));
        });
    }

}

