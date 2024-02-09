package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    MockMvc mockMvc;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        mockMvc.perform(get("/product/create"))
               .andExpect(status().isOk())
               .andExpect(view().name("createProduct"));
    }

    Product createProduct(String productId, String productName, int productQuantity) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductQuantity(productQuantity);
        return product;
    }

    @SuppressWarnings("null")
    @Test
    void testCreateProduct() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setViewResolvers(new 
                    InternalResourceViewResolver("/WEB-INF/views/", ".html"))
                        .build();

        String requestBody = "productName=Sampo Cap Bambang&productQuantity=5";

        mockMvc.perform(post("/product/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(requestBody))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    
        verify(productService, times(1))
            .createProduct(argThat(product -> 
                product.getProductName().equals("Sampo Cap Bambang") && 
                product.getProductQuantity() == 5));
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> products = new ArrayList<>();

        Product product1 = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100);

        Product product2 = createProduct(
            "857b3c84-8eab-4296-8ca9-6773ffd86517", 
            "Sampo Cap Usep", 
            50);

        products.add(product1);
        products.add(product2);

        when(productService.findAll()).thenReturn(products);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        mockMvc.perform(get("/product/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("listProduct"))
               .andExpect(model().attribute("products", products));
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = createProduct(
            "857b3c84-8eab-4296-8ca9-6773ffd86517", 
            "Sampo Cap Usep", 
            50
        );
        
        when(productService.findById(product.getProductId())).thenReturn(product);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        mockMvc.perform(get("/product/edit/" + product.getProductId().toString()))
               .andExpect(status().isOk())
               .andExpect(view().name("editProduct"))
               .andExpect(model().attributeExists("product"));
    }

    @SuppressWarnings("null")
    @Test
    void testEditProduct() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setViewResolvers(new 
                    InternalResourceViewResolver("/WEB-INF/views/", ".html"))
                        .build();
        
        Product product1 = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100
        );
        
        String requestBody = "productName=Sampo Cap Asep&productQuantity=10";

        mockMvc.perform(put("/product/edit/" + product1.getProductId().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(requestBody))
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("../list")); 

        verify(productService, times(1))
            .editProduct(argThat(product -> 
                product.getProductId().equals(product1.getProductId()) && 
                product.getProductName().equals("Sampo Cap Asep") && 
                product.getProductQuantity() == 10));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        Product product1 = createProduct(
            "6f1238f8-d13a-4e5b-936f-e55156158104", 
            "Sampo Cap Bambang", 
            100
        );

        mockMvc.perform(delete("/product/delete/" + product1.getProductId().toString()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("../list"));
        
        verify(productService, times(1)).deleteProduct(product1.getProductId());
    }
}   
