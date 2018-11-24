package dao;

import model.User;

public interface UserDao {

    void saveUser(User user);

    void removeUser(User user);

    User findUserById(Long id);

    void updateUser(User user);
}
