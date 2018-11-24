package dao;

import model.Card;
import model.User;
import static util.PersistenceContextOperations.*;
import javax.persistence.EntityManagerFactory;

public class CardDaoImpl implements CardDao {

    private EntityManagerFactory emf;

    CardDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void saveCard(User user, Card card) {
        performPersistenceContextOperationWithouReturnData(entityManager -> {
            user.addCard(card);
            entityManager.merge(user);
            entityManager.persist(card);
        });
    }

    @Override
    public void removeCard(Card card) {
        throw new UnsupportedOperationException(); //todo implement method
    }

    @Override
    public Card findCardById(Long id) {
        throw new UnsupportedOperationException(); //todo implement method
    }

    @Override
    public void updateCard(Card card) {
        throw new UnsupportedOperationException(); //todo implement method

    }
}
