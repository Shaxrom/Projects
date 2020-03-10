package dbService.dao;
/**
 * For example,i only implements UserDao
 */

import base.dataSets.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAO {
    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public void save(User user) {
        session.save(user);
        session.close();
    }

    public User read(long id) {
        return (User) session.load(User.class, id);
    }

    public User readByName(String name) {
        Criteria criteria = session.createCriteria(User.class);
        return (User) criteria.add(Restrictions.eq("name", name)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<User> readAll() {
        Criteria criteria = session.createCriteria(User.class);
        return (List<User>) criteria.list();
    }
}