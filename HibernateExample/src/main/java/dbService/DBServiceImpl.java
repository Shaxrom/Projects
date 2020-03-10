package dbService;

/**
 * For example,i only implements UserDao
 */

import base.DBService;
import base.dataSets.User;
import dbService.dao.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DBServiceImpl implements DBService {
    private SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Exam.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Subject.class);
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:8080/db_onlyHibernateExample");
        configuration.setProperty("hibernate.connection.username", "username");
        configuration.setProperty("hibernate.connection.password", "password");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String status = transaction.getLocalStatus().toString();
        session.close();
        return status;
    }

    public void save(UserDataSet dataSet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDAO dao = new UserDAO(session);
        dao.save(dataSet);
        transaction.commit();
    }

    public UserDataSet read(long id) {
        Session session = sessionFactory.openSession();
        UserDAO dao = new UserDAO(session);
        return dao.read(id);
    }

    public UserDataSet readByName(String name) {
        Session session = sessionFactory.openSession();
        UserDAO dao = new UserDAO(session);
        return dao.readByName(name);
    }

    public List<UserDataSet> readAll() {
        Session session = sessionFactory.openSession();
        UserDAO dao = new UserDAO(session);
        return dao.readAll();
    }

    public void shutdown(){
        sessionFactory.close();
    }
}