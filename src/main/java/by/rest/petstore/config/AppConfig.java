package by.rest.petstore.config;

import by.rest.petstore.model.Order;
import by.rest.petstore.model.Pet;
import by.rest.petstore.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class AppConfig {

    @Bean
    public List<User> getUsers() {
        return new ArrayList<>();
    }

    @Bean
    public Map<String, User> getUsersMap() {
        return new LinkedHashMap<>();
    }

    @Bean
    public Map<Long, String> getUserTokens() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Pet> getPetsMap() {
        return new LinkedHashMap<>();
    }

    @Bean
    public Map<Long, Order> getOrdersMap() {
        return new LinkedHashMap<>();
    }
}
