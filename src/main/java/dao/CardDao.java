package dao;

import model.Card;
import model.User;

public interface CardDao {

    void saveCard(User user, Card card);

    void removeCard(Card card);

    Card findCardById(Long id);

    void updateCard(Card card);
}
