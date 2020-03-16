package java.services;

import java.forms.LoginForm;
import java.transfer.TokenDto;

public interface LoginService {
    TokenDto login(LoginForm loginForm);
}
