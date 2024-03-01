package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;

class OrderRepositoryTest {
    OrderRepository orderRepository;
    List<Order> orders;
    
    @BeforeEach
    void setup() {
        orderRepository = new OrderRepository();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("e45d7d21-fd29-4533-a569-abbe0819579a");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        product1.setProductQuantity(2);

        orders = new ArrayList<>();
        Order order1 = new Order("dbd4aff4-9a7f-4603-92c2-eaf529271cc9", 
            products, 1708560000L, "Safira Sudrajat");
        Order order2 = new Order("c0f81308-9911-40c5-8da4-fa3194485aa1",
            products, 1708570000L, "Safira Sudrajat");
        Order order3 = new Order("f3e3e3e3-3e3e-3e3e-3e3e-3e3e3e3e3e3e",
            products, 1708580000L, "Safira Sudrajat");
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @Test
    void testSaveCreate() {
        Order order = orders.get(1);
        Order result = orderRepository.save(order);

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(order.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Order order = orders.get(1);
        orderRepository.save(order);

        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(), 
            order.getAuthor(), OrderStatus.SUCCESS.getValue());
        Order result = orderRepository.save(newOrder);

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(OrderStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(orders.get(1).getId(), findResult.getId());
        assertEquals(orders.get(1).getOrderTime(), findResult.getOrderTime());
        assertEquals(orders.get(1).getAuthor(), findResult.getAuthor());
        assertEquals(orders.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        Order findResult = orderRepository.findById("zczc");
        assertNull(findResult);
    }

    @Test
    void testFindAllByAuthorIfAuthorCorrect() {
        for (Order order : orders) {
            orderRepository.save(order);
        }

        List<Order> findResult = orderRepository.findAllByAuthor(orders.get(1).getAuthor());
        assertEquals(2, findResult.size());
    }

    @Test
    void testFindAllByAuthorIfAllLowercase() {
        orderRepository.save(orders.get(1));

        List<Order> orderList = orderRepository.findAllByAuthor(orders.get(1).getAuthor().toLowerCase());
        assertTrue(orderList.isEmpty());
    }
}
