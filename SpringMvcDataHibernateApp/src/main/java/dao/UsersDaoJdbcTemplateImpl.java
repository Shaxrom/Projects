package java.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import java.models.Car;
import java.models.User;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class UsersDaoJdbcTemplateImpl implements UsersDao {

    private final String SQL_SELECT_USERS_WITH_CARS =
            "SELECT SpringMvcApp_version2_user.*, SpringMvcApp_version2_car.id as car_id, SpringMvcApp_version2_car.model FROM SpringMvcApp_version2_user LEFT JOIN SpringMvcApp_version2_car ON SpringMvcApp_version2_user.id = SpringMvcApp_version2_car.owner_id";

    private final String SQL_SELECT_USER_WITH_CARS =
            "SELECT SpringMvcApp_version2_user.*, SpringMvcApp_version2_car.id as car_id, SpringMvcApp_version2_car.model FROM SpringMvcApp_version2_user LEFT JOIN SpringMvcApp_version2_car ON SpringMvcApp_version2_user.id = SpringMvcApp_version2_car.owner_id WHERE SpringMvcApp_version2_user.id = ?";

    private final String SQL_SELECT_ALL_BY_FIRST_NAME =
            "SELECT * FROM SpringMvcApp_version2_user WHERE first_name = ?";

    private final String    SQL_INSERT_USER =
            "INSERT INTO SpringMvcApp_version2_user(first_name, last_name) VALUES (:firstName, :lastName)";

    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM SpringMvcApp_version2_user WHERE id = :id";

    private JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Map<Long, User> usersMap = new HashMap<>();

    @Autowired
    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapperWithoutCars = (resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .firstName(resultSet.getString("first_name"))
            .lastName(resultSet.getString("last_name"))
            .build();

    private RowMapper<User> userRowMapper
            = (ResultSet resultSet, int i) -> {
       Long id = resultSet.getLong("id");

       if (!usersMap.containsKey(id)) {
           String firstName = resultSet.getString("first_name");
           String lastName = resultSet.getString("last_name");
           User user = new User(id, firstName, lastName, new ArrayList<>());
           usersMap.put(id, user);
       }

       Car car = new Car(resultSet.getLong("car_id"),
               resultSet.getString("model"), usersMap.get(id));
       usersMap.get(id).getCars().add(car)
       return usersMap.get(id);
    };

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return template.query(SQL_SELECT_ALL_BY_FIRST_NAME, userRowMapperWithoutCars, firstName);
    }

    @Override
    public Optional<User> find(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<User> result = namedParameterJdbcTemplate.query(SQL_SELECT_BY_ID, params, userRowMapperWithoutCars);

        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    @Override
    public void save(User model) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", model.getFirstName());
        params.put("lastName", model.getLastName());
        namedParameterJdbcTemplate.update(SQL_INSERT_USER, params);
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findAll() {
        List<User> result =  template.query(SQL_SELECT_USERS_WITH_CARS, userRowMapper);
        usersMap.clear();
        return result;
    }
}