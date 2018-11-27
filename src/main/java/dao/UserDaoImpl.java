package dao;

import model.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static util.PersistenceContextOperations.*;

public class UserDaoImpl implements UserDao {

    EntityManagerFactory emf;

    UserDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void saveUser(User user) {
        performPersistenceContextOperationWithoutReturnData(entityManager ->
                entityManager.persist(user));
    }

    @Override
    public void removeUser(User user) {
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            User removedUser = entityManager.merge(user);
            entityManager.remove(removedUser);
        });
    }

    @Override
    public User findUserById(Long id) {
        return performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAllUsers() {
        return performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select u from User u", User.class)
                .getResultList());
    }

    @Override
    public User updateUser(User user) {
        return performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.merge(user));
    }
}
