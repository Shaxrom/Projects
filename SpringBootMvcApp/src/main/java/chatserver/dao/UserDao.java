package chatserver.dao;

import chatserver.model.User;

import java.util.List;

public interface UserDao {

    User getByLogin(String login);

    void save(User user);

    List<User> findAll();
}
