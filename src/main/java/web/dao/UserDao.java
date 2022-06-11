package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    User getUserById(int id);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    List<User> getAllUsers();
}
