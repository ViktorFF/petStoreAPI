package by.rest.petstore.service;

import by.rest.petstore.model.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StoreService {
    private Map<Long, Order> ordersMap;

    public StoreService(Map<Long, Order> ordersMap) {
        this.ordersMap = ordersMap;
    }

    public Map<Long, Order> getOrdersMap() {
        return ordersMap;
    }

    public void addOrder(Order order) {
        ordersMap.put(order.getId(), order);
    }

    public void deleteOrder(Long id) {
        ordersMap.remove(id);
    }
}
