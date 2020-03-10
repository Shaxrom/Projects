package My.dao;

import My.Entity.User;

import java.util.List;

public interface UserDAO {

    void save(User user);

    User getById(int id);

    List<User> findAll();

    void update(User user);

    void delete(int id);

}
