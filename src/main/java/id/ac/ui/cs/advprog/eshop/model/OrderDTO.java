package id.ac.ui.cs.advprog.eshop.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private List<Product> products;
    private String author;

    public OrderDTO() {
        products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        this.products.add(p);
    }
}
