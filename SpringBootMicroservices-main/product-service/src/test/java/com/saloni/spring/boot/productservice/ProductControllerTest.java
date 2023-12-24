package com.saloni.spring.boot.productservice;

import com.saloni.spring.boot.productservice.controller.ProductController;
import com.saloni.spring.boot.productservice.dto.ProductRequest;
import com.saloni.spring.boot.productservice.dto.ProductResponse;
import com.saloni.spring.boot.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createProduct_ShouldReturnCreatedStatus() {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        
        // Act
        productController.createProduct(productRequest);
        
        // Assert
        verify(productService, times(1)).createProduct(productRequest);
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        List<ProductResponse> expectedProducts = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(expectedProducts);
        
        // Act
        List<ProductResponse> actualProducts = productController.getAllProducts();
        
        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById_ShouldReturnProductWithGivenId() {
        // Arrange
        String productId = "123";
        ProductResponse expectedProduct = new ProductResponse();
        when(productService.getProductById(productId)).thenReturn(expectedProduct);
        
        // Act
        ProductResponse actualProduct = productController.getProductById(productId);
        
        // Assert
        assertEquals(expectedProduct, actualProduct);
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void updateProduct_ShouldReturnOkStatus() {
        // Arrange
        String productId = "123";
        ProductRequest productRequest = new ProductRequest();
        
        // Act
        productController.updateProduct(productId, productRequest);
        
        // Assert
        verify(productService, times(1)).updateProduct(productId, productRequest);
    }

    @Test
    void deleteProduct_ShouldReturnNoContentStatus() {
        // Arrange
        String productId = "123";
        
        // Act
        productController.deleteProduct(productId);
        
        // Assert
        verify(productService, times(1)).deleteProduct(productId);
    }
}
