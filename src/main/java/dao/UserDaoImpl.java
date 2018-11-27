package dao;

import model.User;
import util.PersistenceContextOperations;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static util.PersistenceContextOperations.*;

public class UserDaoImpl implements UserDao {

    private PersistenceContextOperations persistenceUtil;

    UserDaoImpl(EntityManagerFactory emf)
    {
        this.persistenceUtil = new PersistenceContextOperations(emf);
    }

    @Override
    public void saveUser(User user) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager ->
                entityManager.persist(user));
    }

    @Override
    public void removeUser(User user) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager -> {
            User removedUser = entityManager.merge(user);
            entityManager.remove(removedUser);
        });
    }

    @Override
    public User findUserById(Long id) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAllUsers() {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select u from User u", User.class)
                .getResultList());
    }

    @Override
    public User updateUser(User user) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.merge(user));
    }
}
