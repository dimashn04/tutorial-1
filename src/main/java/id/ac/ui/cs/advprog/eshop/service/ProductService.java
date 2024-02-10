package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> findAll();
    Product findById(String productId);
    Product editProduct(Product editedProduct);
    Product deleteProduct(String productId);
}
