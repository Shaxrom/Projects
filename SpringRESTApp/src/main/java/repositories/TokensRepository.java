package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import models.Token;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String value);
}
