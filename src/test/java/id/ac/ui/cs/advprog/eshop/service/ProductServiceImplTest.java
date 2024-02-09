package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @BeforeEach
    void setup() {}

    Product createProduct(String productId, String productName, int productQuantity) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductQuantity(productQuantity);
        return product;
    }

    @Test
    void testCreateProduct() {
        Product product = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100);

        when(productRepository.create(product)).thenReturn(product);

        Product savedProduct = productServiceImpl.createProduct(product);
        assertEquals(product.getProductId(), savedProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProduct() {
        List<Product> productList = new ArrayList<>();
        Product product = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100);

        Product product2 = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158105", 
            "Sampo Cap Bambang", 
            100);

        productList.add(product);
        productList.add(product2);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productServiceImpl.findAll();

        assertEquals(productList.size(), result.size());
        for (int i = 0; i < productList.size(); i++) {
            assertEquals(productList.get(i), result.get(i));
        }
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        Product product = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100);
        
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product foundProduct = productServiceImpl.findById(product.getProductId());

        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void testEditProduct() {
        Product product = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100);
        
        when(productRepository.edit(product)).thenReturn(product);

        Product editedProduct = productServiceImpl.editProduct(product);

        assertEquals(product, editedProduct);
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDeleteProduct() {
        Product product = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100);
        
        when(productRepository.delete(product.getProductId())).thenReturn(product);

        Product deletedProduct = productServiceImpl.deleteProduct(product.getProductId());

        assertEquals(product, deletedProduct);
        verify(productRepository, times(1)).delete(product.getProductId());
    }
}
