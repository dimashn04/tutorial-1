package id.ac.ui.cs.advprog.eshop.model;

import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {
    String id;
    List<Product> products;
    Long orderTime;
    String author;
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";

        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one product");
        } else {
            this.products = products;
        }
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        
        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELED"};
        if (Arrays.stream(statusList).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException("Invalid status value");
        } else {
            this.status = status;
        }
    }

    public void setStatus(String status) {
        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELED"};
        if (Arrays.stream(statusList).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException("Invalid status value");
        } else {
            this.status = status;
        }
    }
}
