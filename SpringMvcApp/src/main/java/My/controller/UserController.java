package My.controller;

import My.Entity.User;
import My.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/users")
    public String getUsers(Model model ){
        model.addAttribute("users",userService.findAll());
        return "userList";
    }

    @GetMapping("/user/{id}")
    public String getById(@PathVariable("id") int id,Model model){
        model.addAttribute("user",userService.getById(id));
        return "showUser";
    }

    @GetMapping("/addUser")
    public String createUsePage(){
        return "createUser";
    }

    @PostMapping("/adddUser")
    public String addUser(@ModelAttribute("user")User user){
        userService.save(user);
        return "redirect/users";
    }

}
