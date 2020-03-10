package chatserver.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import chatserver.model.User;
import chatserver.service.ChatService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;


    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest()
                    .body("Short name");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest()
                    .body("Long name");
        }

        User alreadyLoggedIn = chatService.getLoggedIn(name);
        if (alreadyLoggedIn != null) {
            return ResponseEntity.badRequest()
                    .body("Already logged in");
        }
        chatService.login(name);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity logout(@RequestParam("name") String name) {
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity online() {

        List<User> online = chatService.getOnlineUsers();
        String responseBody = online.stream()
                .map(User::getLogin)
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok().body(responseBody);
    }

    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(
            path = "chat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> chat() {
        return ResponseEntity.badRequest().build();
    }
}
