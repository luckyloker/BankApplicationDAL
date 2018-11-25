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
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            user.addCard(card);
            entityManager.merge(user);
            entityManager.persist(card);
        });
    }

    @Override
    public void removeCard(Card card) {
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            Card removedCard = entityManager.merge(card);
            User removedCardUser = entityManager.find(User.class, removedCard.getUser().getId());
            removedCardUser.removeCard(card);
            entityManager.remove(removedCard);
        });
    }

    @Override
    public Card findCardById(Long id) {
        return performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.find(Card.class, id));
    }

    @Override
    public void updateCard(Card card) {
        performPersistenceContextOperationWithoutReturnData(entityManager ->
                entityManager.merge(card));
    }
}
