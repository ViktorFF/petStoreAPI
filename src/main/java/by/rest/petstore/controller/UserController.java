package by.rest.petstore.controller;

import by.rest.petstore.exception.user.AuthenticationUserException;
import by.rest.petstore.exception.user.DeleteUserException;
import by.rest.petstore.exception.user.LoginUserException;
import by.rest.petstore.exception.user.UserNotFoundException;
import by.rest.petstore.model.ApiRequest;
import by.rest.petstore.model.ApiResponse;
import by.rest.petstore.model.User;
import by.rest.petstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username,
                                        @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        Map<String, User> usersMap = userService.getUsersMap();
        if (!usersMap.containsKey(username)) throw new UserNotFoundException();
        return new ResponseEntity<>(usersMap.get(username), HttpStatus.OK);
    }

    @PutMapping(path = "/{username}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String username,
                                                  @Valid @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        Map<String, User> usersMap = userService.getUsersMap();
        if (!usersMap.containsKey(username)) throw new UserNotFoundException();
        usersMap.put(username, request.getUser());
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{username}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String username,
                                                  @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        Map<String, User> usersMap = userService.getUsersMap();
        if (!usersMap.containsKey(username)) throw new UserNotFoundException();
        if (userService.getTokens().get(userService.getUsersMap().get(username).getId()).equals(request.getToken())) throw new DeleteUserException();
        usersMap.remove(username);
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody ApiRequest request) {
        String token = userService.authentication(request.getUser());
        if (token == null) throw new AuthenticationUserException();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(path = "logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        userService.getTokens().values().remove(request.getToken());
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @PostMapping(path = "/createWithList")
    public ResponseEntity<ApiResponse> createWithList(@Valid @RequestBody List<User> users) {
        userService.addAllUsers(users);
        return new ResponseEntity<>(new ApiResponse(200,"Success", "Successful operation"), HttpStatus.OK);
    }
}
