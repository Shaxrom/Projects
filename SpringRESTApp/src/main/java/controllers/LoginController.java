package java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.forms.LoginForm;
import java.LoginService;
import javae.transfer.TokenDto;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody  LoginForm loginForm) {
        return ResponseEntity.ok(loginService.login(loginForm));
    }
}
