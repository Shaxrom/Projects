package java.app;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.mvc.dao.UsersDao;
import java.dao.UsersDaoJdbcTemplateImpl;
import java.models.User;
import javax.persistence.EntityManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.mysql.Driver");
        dataSource.setUsername("username");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:mysql://localhost:8080/new_SpringMvcApp_version2_db");

        UsersDao usersDao = new UsersDaoJdbcTemplateImpl(dataSource);

        List<User> users = usersDao.findAll();

        System.out.println(users);
    }
}
