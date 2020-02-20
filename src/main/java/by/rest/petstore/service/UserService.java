package by.rest.petstore.service;

import by.rest.petstore.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class UserService {
    private Map<String, User> usersMap;
    private Map<Long, String> tokens;

    public UserService(Map<String, User> usersMap, Map<Long, String> tokens) {
        this.usersMap = usersMap;
        this.tokens = tokens;
    }

    public Map<String, User> getUsersMap() {
        return usersMap;
    }

    public Map<Long, String> getTokens() {
        return tokens;
    }

    public void addUser(User user) {
        usersMap.put(user.getUsername(), user);
    }

    public void addAllUsers(List<User> userList) {
        for (User user: userList) {
            usersMap.put(user.getUsername(), user);
        }
    }

    public String authentication(User newUser) {
        String login = newUser.getUsername();
        String password = newUser.getPassword();
        if (usersMap.isEmpty()) {
            return null;
        }
        if (usersMap.containsKey(login)) {
            if (usersMap.get(login).getPassword().equals(password)) {
                String token = Integer.toString(new Random().nextInt());
                tokens.put(usersMap.get(login).getId(), token);
                return token;
            }
        }
        return null;
    }
}
