package chatserver.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chatserver.controller.ChatController;
import chatserver.dao.MessageDao;
import chatserver.dao.UserDao;
import chatserver.model.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private UserDao userDao;

    @Nullable
    @Transactional
    public User getLoggedIn(@NotNull String name) {
        return userDao.getByLogin(name);
    }

    @Transactional
    public void login(@NotNull String login) {
        User user = new User();
        userDao.save(user.setLogin(login));
        log.info("[" + login + "] logged in");
    }

    @NotNull
    @Transactional
    public List<User> getOnlineUsers() {
        return new ArrayList<>(userDao.findAll());
    }
}