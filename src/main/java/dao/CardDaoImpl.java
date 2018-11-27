package dao;

import model.Card;
import model.User;
import util.PersistenceContextOperations;

import static util.PersistenceContextOperations.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CardDaoImpl implements CardDao {

    private PersistenceContextOperations persistenceUtil;

    CardDaoImpl(EntityManagerFactory emf) {
        this.persistenceUtil = new PersistenceContextOperations(emf);
    }

    @Override
    public void saveCard(User user, Card card) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager -> {
            card.setUser(user);
            entityManager.persist(card);
        });
    }

    @Override
    public void removeCard(Card card) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager -> {
            Card removedCard = entityManager.merge(card);
//            User userToRemoveCard = entityManager.find(User.class, removedCard.getUser().getId());
//            userToRemoveCard.removeCard(removedCard);
            entityManager.remove(removedCard);
        });
    }

    @Override
    public Card findCardById(Long id) {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.find(Card.class, id));
    }

    @Override
    public void updateCard(Card card) {
        persistenceUtil.performPersistenceContextOperationWithoutReturnData(entityManager ->
                entityManager.merge(card));
    }

    @Override
    public List<Card> findAllCards() {
        return persistenceUtil.performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select c from Card c", Card.class)
                        .getResultList());
    }
}
