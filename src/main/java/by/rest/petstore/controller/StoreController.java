package by.rest.petstore.controller;

import by.rest.petstore.exception.store.OrderNotFoundException;
import by.rest.petstore.exception.user.LoginUserException;
import by.rest.petstore.model.ApiRequest;
import by.rest.petstore.model.ApiResponse;
import by.rest.petstore.model.Order;
import by.rest.petstore.service.StoreService;
import by.rest.petstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/store")
public class StoreController {
    private UserService userService;
    private StoreService storeService;

    public StoreController(UserService userService, StoreService storeService) {
        this.userService = userService;
        this.storeService = storeService;
    }

    @GetMapping(path = "/order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId,
                                              @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (!storeService.getOrdersMap().containsKey(orderId)) throw new OrderNotFoundException();
        return new ResponseEntity<>(storeService.getOrdersMap().get(orderId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/order/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId,
                                                   @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (!storeService.getOrdersMap().containsKey(orderId)) throw new OrderNotFoundException();
        storeService.deleteOrder(orderId);
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }

    @PostMapping(path = "/order")
    public ResponseEntity<ApiResponse> addOrder(@Valid @RequestBody ApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        storeService.addOrder(request.getOrder());
        return new ResponseEntity<>(new ApiResponse(200, "Success", "Successful operation"), HttpStatus.OK);
    }
}
