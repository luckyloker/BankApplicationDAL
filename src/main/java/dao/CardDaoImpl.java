package dao;

import model.Card;
import model.User;

import static util.PersistenceContextOperations.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CardDaoImpl implements CardDao {

    private EntityManagerFactory emf;

    CardDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void saveCard(User user, Card card) {
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            User userToAddCard = entityManager.find(User.class, user.getId());
            userToAddCard.addCard(card);
        });
    }

    @Override
    public void removeCard(Card card) {
        performPersistenceContextOperationWithoutReturnData(entityManager -> {
            Card removedCard = entityManager.merge(card);
            User userToRemoveCard = entityManager.find(User.class, removedCard.getUser().getId());
            userToRemoveCard.removeCard(removedCard);
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

    @Override
    public List<Card> findAllCards() {
        return performPersistenceContextOperationWithReturnData(entityManager ->
                entityManager.createQuery("select c from Card c", Card.class)
                        .getResultList());
    }
}
