package java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.forms.UserForm;
import java.models.User;
import java.repositories.UsersRepository;
import java.services.UsersService;
import java.transfer.UserDto;
import java.util.List;

import static java.transfer.UserDto.from;

@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return from(usersService.findAll());
    }

    @GetMapping("/users/{user-id}")
    public User getUser(@PathVariable("user-id") Long userId) {
        return usersService.findOne(userId);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody UserForm userForm) {
        usersService.signUp(userForm);
        return ResponseEntity.ok().build();
    }
}
