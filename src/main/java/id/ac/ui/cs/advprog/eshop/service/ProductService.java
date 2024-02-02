package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product createProduct(Product product);
    public List<Product> findAll();
    public Product findById(String productId);
    public Product editProduct(Product editedProduct);
}
