package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Order;

public class OrderRepository {
    private List<Order> orderData = new ArrayList<>();
    public Order save(Order order) {return null;}
    public Order findById(String id) {return null;}
    public List<Order> findAllByAuthor(String author) {return null;}
}
