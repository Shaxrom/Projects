package java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.models.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findAllByFirstName(String firstName);
}
