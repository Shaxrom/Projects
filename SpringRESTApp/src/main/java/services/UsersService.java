package java.services;

import java.forms.UserForm;
import java.models.User;
import java.util.List;

public interface UsersService {
    void signUp(UserForm userForm);

    List<User> findAll();

    User findOne(Long userId);
}
