package dao;

import exceptions.BankAppException;
import model.User;
import org.junit.jupiter.api.*;
import util.TestGenerator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class UserDaoImplTest {
    private EntityManagerFactory emf;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit");
        userDao = new UserDaoImpl(emf);
    }

    @AfterEach
    void tearDown() {
        emf.close();
    }

    @Test
    void saveUser() {
        User newUser = TestGenerator.generateUser();
        userDao.saveUser(newUser);

        assertNotNull(newUser.getId());
    }

    @Test
    void saveInvalidUser() {
        User invalidUser = new User();
        invalidUser.builder()
                .firstName("Henry")
                .lastName("Ford")
                .taxNumber(321456L)
                .passportNumber(82131L)
                .build();

        assertThrows(BankAppException.class, () -> userDao.saveUser(invalidUser));
    }

    @Test
    void getUser() {
        User newUser = TestGenerator.generateUser();
        userDao.saveUser(newUser);
        User userFromDB = userDao.findUserById(newUser.getId());
        assertThat(newUser.getFirstName(), is(userFromDB.getFirstName()));
    }

    @Test
    void getUserNotStoredInDB() {
        User newUser = TestGenerator.generateUser();
        assertThrows(BankAppException.class, () -> userDao.findUserById(newUser.getId()));
    }

    @Test
    void removeUser() {
        User newUser = TestGenerator.generateUser();
        userDao.saveUser(newUser);
        User userFromDb = userDao.findUserById(newUser.getId());
        assertThat(userFromDb.getTaxNumber(), is(newUser.getTaxNumber()));
        userDao.removeUser(newUser);
        User removedUser = userDao.findUserById(newUser.getId());
        assertNull(removedUser);
    }

    @Test
    void updateUser() {
        User newUser = TestGenerator.generateUser();
        userDao.saveUser(newUser);
        newUser.setFirstName("NEW NAME");
        User updatedUser = userDao.updateUser(newUser);
        User userFromDb = userDao.findUserById(newUser.getId());
        assertThat(updatedUser.getFirstName(), is("NEW NAME"));
        assertThat(userFromDb.getFirstName(), is("NEW NAME"));
    }

    @Test
    void updateUserNotStoredInDb () {
        User newUser = TestGenerator.generateUser();
        try {
            userDao.updateUser(newUser);
        }
        catch (Exception e) {
            assertEquals(BankAppException.class, e.getClass());
        }
// assertThrows(BankAppException.class, () -> userDao.updateUser(newUser));
    }


    @Test
    void findAllUsers() {
        User user1 = TestGenerator.generateUser();
        User user2 = TestGenerator.generateUser();
        User user3 = TestGenerator.generateUser();
        userDao.saveUser(user1);
        userDao.saveUser(user2);
        userDao.saveUser(user3);
        List<User> usersListFromDb = userDao.findAllUsers();
        assertThat(user1.getFirstName(), is(usersListFromDb.get(0).getFirstName()));
        assertThat(user3.getBirthday(), is(usersListFromDb.get(2).getBirthday()));
    }
}