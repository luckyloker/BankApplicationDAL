package util;

import exceptions.BankAppException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

public class PersistenceContextOperations {
    private final EntityManagerFactory emf;

    public PersistenceContextOperations(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void performPersistenceContextOperationWithoutReturnData(Consumer<EntityManager> entityManagerConsumer) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManagerConsumer.accept(entityManager);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new BankAppException("Transaction is rolled back", e);
        }
        finally {
            entityManager.close();
        }
    }

    public <T> T performPersistenceContextOperationWithReturnData(Function<EntityManager, T> entityManagerTFunction) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            T result = entityManagerTFunction.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new BankAppException("Transaction is rolled back", e);
        }
        finally {
            entityManager.close();
        }
    }

}
