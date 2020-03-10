package My.config;

import My.dao.UserDAO;
import My.dao.UserDaoImpl;
import My.sevice.UserService;
import My.sevice.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Bean
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }
    @Bean
    private DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl();
        dataSource.setUsername();
        dataSource.setPassword();
        dataSource.setDriverClassName();
        return dataSource;

    }
    @Bean
    public UserDAO getUserDAO(){
        return new UserDaoImpl(getJdbcTemplate());
    }
    @Bean
    public UserService getUserService(){
        return new UserServiceImpl();
    }
}
