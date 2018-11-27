package dao;

import model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void removeUser(User user);

    User findUserById(Long id);

    List<User> findAllUsers();

    User updateUser(User user);
}
