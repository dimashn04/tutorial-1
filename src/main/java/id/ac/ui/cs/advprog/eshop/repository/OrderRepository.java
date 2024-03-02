package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Order;

@Repository
public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();

    public Order save(Order order) {
        int i = 0;
        for (Order o : orderData) {
            if (o.getId().equals(order.getId())) {
                orderData.set(i, order);
                return order;
            }
            i++;
        }
        
        orderData.add(order);
        return order;
    }
    public Order findById(String id) {
        for (Order o : orderData) {
            if (o.getId().equals(id)) {
                return o;
            }
        }

        return null;
    }

    public List<Order> findAllByAuthor(String author) {
        List<Order> result = new ArrayList<>();
        for (Order o : orderData) {
            if (o.getAuthor().equals(author)) {
                result.add(o);
            }
        }

        return result;
    }
}
