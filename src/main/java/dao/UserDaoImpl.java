package dao;

import model.User;

import javax.persistence.EntityManagerFactory;
import static util.PersistenceContextOperations.*;

public class UserDaoImpl implements UserDao {

    EntityManagerFactory emf;

    UserDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void saveUser(User user) {
        performPersistenceContextOperationWithouReturnData(entityManager ->
                entityManager.persist(user));
    }

    @Override
    public void removeUser(User user) {
        performPersistenceContextOperationWithouReturnData(entityManager -> {
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
    public void updateUser(User user) {
        performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.merge(user));
    }
}
