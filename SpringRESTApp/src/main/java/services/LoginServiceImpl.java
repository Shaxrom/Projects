package java.services;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.forms.LoginForm;
import java.models.Token;
import java.models.User;
import java.repositories.TokensRepository;
import java.repositories.UsersRepository;
import java.transfer.TokenDto;
import java.util.Optional;

import static java.transfer.TokenDto.from;

public class LoginServiceImpl implements LoginService {

    @Autowired
    private TokensRepository tokensRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public TokenDto login(LoginForm loginForm) {
        Optional<User> userCandidate = usersRepository.findOneByLogin(loginForm.getLogin());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();

            if (passwordEncoder.matches(loginForm.getPassword(), user.getHashPassword())) {
                Token token = Token.builder()
                        .user(user)
                        .value(RandomStringUtils.random(10, true, true))
                        .build();

                tokensRepository.save(token);
                return from(token);
            }
        } throw new IllegalArgumentException("User not found");
    }
}
